package jpabook.jpashop.repository.order;

import java.util.List;
import jpabook.jpashop.domain.Order;
public interface CustomOrderRepository {

    List<Order> findAll(OrderSearch orderSearch);

}
