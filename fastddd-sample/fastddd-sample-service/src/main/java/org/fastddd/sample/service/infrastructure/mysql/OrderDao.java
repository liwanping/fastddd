package org.fastddd.sample.service.infrastructure.mysql;

import org.fastddd.api.dao.DomainObjectDao;
import org.fastddd.sample.service.domain.entity.Order;

public interface OrderDao extends DomainObjectDao<Order, Long> {
}
