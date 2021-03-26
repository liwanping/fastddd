package org.fastddd.core.event.listener;

import org.fastddd.core.event.EventInvocation;
import org.fastddd.api.event.PayloadEvent;

import java.util.Collection;
import java.util.List;

public interface EventListener {

    Class<?> getTargetType();

    List<EventInvocation> generateInvocations(Collection<PayloadEvent> events);
}
