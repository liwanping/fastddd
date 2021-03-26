package org.fastddd.core.event.listener;

import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.PayloadEvent;

import java.util.Collection;
import java.util.List;

public interface EventListener {

    Class<?> getTargetType();

    List<Invocation> generateInvocations(Collection<PayloadEvent> events);
}
