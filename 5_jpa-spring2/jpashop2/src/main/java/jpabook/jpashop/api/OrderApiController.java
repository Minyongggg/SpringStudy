package jpabook.jpashop.api;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.dto.OrderDto;
import jpabook.jpashop.repository.order.dto.OrderFlatDto;
import jpabook.jpashop.repository.order.dto.OrderItemQueryDto;
import jpabook.jpashop.repository.order.dto.OrderQueryDto;
import jpabook.jpashop.repository.order.dto.OrderSearchDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderJpaRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderJpaRepository.findAll(new OrderSearchDto());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItem.getItem().getName();
            }
        }
        return orders;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderJpaRepository.findAll(new OrderSearchDto());
        return orders.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3() {
        List<Order> orders = orderJpaRepository.findAllWithItem();
        return orders.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3Page(
        @RequestParam(value = "offset", defaultValue = "0") int offset,
        @RequestParam(value = "limit", defaultValue = "100") int limit
        ) {
        List<Order> orders = orderJpaRepository.findAllWithMemberDelivery(offset, limit);
        return orders.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    // Spring Data Jpa의 JpaRepository.findAll은 기본적으로 fetch join을 하나?
    // -> 안한다
    // fetch join을 하는 커스텀 메소드 필요
    // 근데 @Query 어노테이션으로 fetch join 쿼리을 짜게되면 페이징은?
    // -> 커스텀 인터페이스와 구현체를 만들어서 OrderRepository에 다중상속하고 순수 JPQL이나 QueryDsl을 사용하여 메소드 작성
    @GetMapping("/api/v3.2/orders")
    public List<OrderDto> orderV3SpringDataJpa() {
        List<Order> orders = orderJpaRepository.findAll();
        return orders.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDtoOptimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDtoFlat();

        return flats.stream()
            .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
            )).entrySet().stream()
            .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
            .collect(toList());
    }
}
