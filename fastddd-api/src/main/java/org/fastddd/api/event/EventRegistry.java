package org.fastddd.api.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage the payload event to register or unregister
 * @author: frank.li
 * @date: 2021/3/29
 */
public final class EventRegistry {

    private static final ThreadLocal<List<PayloadEvent>> TL = ThreadLocal.withInitial(ArrayList::new);

    public static void register(PayloadEvent payloadEvent) {
        TL.get().add(payloadEvent);
    }

    public static List<PayloadEvent> unregisterAll() {
        List<PayloadEvent> payloadEvents = new ArrayList<>(TL.get());
        TL.get().clear();
        return payloadEvents;
    }

    public static List<PayloadEvent> get() {
        return TL.get();
    }

    public static void remove() {
        TL.remove();
    }
}
