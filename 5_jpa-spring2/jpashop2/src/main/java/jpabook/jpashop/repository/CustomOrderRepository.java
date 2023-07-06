package jpabook.jpashop.repository;

import java.util.List;
import jpabook.jpashop.domain.Order;
public interface CustomOrderRepository {

    List<Order> findAll(OrderSearch orderSearch);

}
