package jpabook.jpashop.repository.order.dto;

import jpabook.jpashop.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;
    public OrderItemDto(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}