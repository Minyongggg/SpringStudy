package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.dto.SimpleOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        return orderRepository.findAll();
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    }
}
