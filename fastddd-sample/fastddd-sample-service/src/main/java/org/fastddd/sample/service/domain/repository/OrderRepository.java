package org.fastddd.sample.service.domain.repository;

import org.fastddd.api.repository.DaoAwareRepository;
import org.fastddd.sample.service.domain.entity.Order;
import org.fastddd.sample.service.domain.entity.OrderLine;
import org.fastddd.sample.service.infrastructure.mysql.OrderLineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class OrderRepository extends DaoAwareRepository<Order, Long> {

    @Autowired
    private OrderLineDao orderLineDao;

    public OrderRepository() {
        super(Order.class);
    }

    protected void doInsertChildComponents(Collection<Order> orders) {

        List<OrderLine> orderLines = new ArrayList<>();
        for (Order order : orders) {
            orderLines.addAll(order.getOrderLines());
        }
        orderLineDao.insertAll(orderLines);
    }
}
