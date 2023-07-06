package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockExeption;
import jpabook.jpashop.repository.order.OrderRepositoryOld;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired
    OrderRepositoryOld orderRepository;

    @Test
    @DisplayName("상품주문")
    public void order() throws Exception {
        //given
        Member member = createMember();

        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals(order.getStatus(), OrderStatus.ORDER, "상품 주문 시 상태는 ORDER");
        assertEquals(order.getOrderItems().size(), 1, "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(order.getTotalPrice(), 10000 * orderCount, "주문 가격이 정확해야 한다.");
        assertEquals(item.getStockQuantity(), 8, "주문 수량만큼 재고가 줄어야 한다.");
    }


    @Test
    @DisplayName("주문취소")
    public void cancelOrder() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals(order.getStatus(), OrderStatus.CANCEL, "주문 취소 시 상태는 CANCEL이다.");
        assertEquals(item.getStockQuantity(), 10, "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    @Test
    @DisplayName("주문 재고수량 초과 예외")
    public void exceedStockQuantity() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        //when & then
        assertThrows(NotEnoughStockExeption.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}
