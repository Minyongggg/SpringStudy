package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {

        return em.createQuery("select o from Order o join o.member m " +
                "where o.status = :status " +
                "and m.name like :name", Order.class)
            .setParameter("status", orderSearch.getOrderStatus())
            .setParameter("name", orderSearch.getMemberName())
            .getResultList();
    }
}
