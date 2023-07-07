package jpabook.jpashop.repository.order;

import java.util.List;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.dto.OrderSearchDto;

public interface CustomOrderRepository {

    List<Order> findAll(OrderSearchDto orderSearch);

    List<Order> findAllWithMemberDelivery(int limit, int offset);

    List<Order> findAllWithItem();

}
