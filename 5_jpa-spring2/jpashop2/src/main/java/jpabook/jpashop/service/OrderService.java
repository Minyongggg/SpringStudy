package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.member.MemberRepository;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.dto.OrderSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    //취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearchDto orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
