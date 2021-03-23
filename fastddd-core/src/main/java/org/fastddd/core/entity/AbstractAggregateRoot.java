package org.fastddd.core.entity;

import org.fastddd.core.event.PayloadEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAggregateRoot<ID extends Serializable> extends AbstractDomainObject<ID> implements AggregateRoot<ID> {

    private final List<PayloadEvent> payloadEvents = new ArrayList<>();

    @Override
    public void registerEvent(PayloadEvent payloadEvent) {
        payloadEvents.add(payloadEvent);
    }

    @Override
    public Collection<? extends PayloadEvent> getUncommittedEvents() {
        return payloadEvents;
    }

    @Override
    public void commitEvents() {
        payloadEvents.clear();
    }
}
