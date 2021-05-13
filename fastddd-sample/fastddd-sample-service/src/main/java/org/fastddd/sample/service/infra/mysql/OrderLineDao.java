package org.fastddd.sample.service.infra.mysql;

import org.fastddd.api.dao.BatchDomainObjectDao;
import org.fastddd.sample.service.domain.entity.OrderLine;

import java.util.List;

public interface OrderLineDao extends BatchDomainObjectDao<OrderLine, Long> {

    List<OrderLine> findByOrderId(Long orderId);
}
