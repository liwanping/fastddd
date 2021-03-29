package org.fastddd.core.event.listener;

import org.fastddd.common.invocation.Invocation;
import org.fastddd.api.event.PayloadEvent;

import java.util.Collection;
import java.util.List;

/**
 * Event listener to respond on payload events
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface EventListener {

    List<Invocation> onEvent(Collection<PayloadEvent> events);
}
