package org.fastddd.api.event;

import java.util.ArrayList;
import java.util.List;

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
