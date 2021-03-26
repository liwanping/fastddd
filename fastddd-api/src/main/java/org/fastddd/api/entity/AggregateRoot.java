package org.fastddd.api.entity;

import org.fastddd.api.event.PayloadEvent;

import java.io.Serializable;
import java.util.Collection;

public interface AggregateRoot<ID extends Serializable> extends DomainObject<ID> {

    void registerEvent(PayloadEvent payloadEvent);

    Collection<? extends PayloadEvent> getUncommittedEvents();

    void commitEvents();
}
