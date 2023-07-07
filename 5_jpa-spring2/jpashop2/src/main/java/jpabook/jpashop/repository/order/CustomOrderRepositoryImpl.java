package jpabook.jpashop.repository.order;

import static jpabook.jpashop.domain.QDelivery.delivery;
import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;
import static jpabook.jpashop.domain.QOrderItem.orderItem;
import static jpabook.jpashop.domain.item.QItem.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.order.dto.OrderSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Slf4j
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

    private final JPAQueryFactory queryFactory;

    public List<Order> findAll(OrderSearchDto orderSearch) {

        return queryFactory
            .select(order)
            .from(order)
            .join(order.member, member)
            .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
            .limit(1000)
            .fetch();
    }
    private BooleanExpression statusEq(OrderStatus status) {
        if (status == null) {
            return null;
        }
        return order.status.eq(status);
    }

    private static BooleanExpression nameLike(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            return null;
        }
        return member.name.like(memberName);
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {

        return queryFactory
            .select(order)
            .from(order)
            .join(order.member, member)
            .fetchJoin()
            .join(order.delivery, delivery)
            .fetchJoin()
            .offset(offset)
            .limit(limit)
            .fetch();
    }

    public List<Order> findAllWithItem() {

        return queryFactory
            .select(order)
            .from(order)
            .join(order.member, member)
            .fetchJoin()
            .join(order.delivery, delivery)
            .fetchJoin()
            .join(order.orderItems, orderItem)
            .fetchJoin()
            .join(orderItem.item, item)
            .fetchJoin()
            .distinct()
            .fetch();
    }
}
