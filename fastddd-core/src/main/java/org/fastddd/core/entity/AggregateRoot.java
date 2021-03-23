package org.fastddd.core.entity;

import org.fastddd.core.event.PayloadEvent;

import java.io.Serializable;
import java.util.Collection;

public interface AggregateRoot<ID extends Serializable> extends DomainObject<ID> {

    void registerEvent(PayloadEvent payloadEvent);

    Collection<? extends PayloadEvent> getUncommittedEvents();

    void commitEvents();
}
