package org.fastddd.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EventSponsor annotation to indicate the method include the event registry logic and
 * the event will be triggered to handle with session operations: commit or cleanupAfterCompletion.
 *
 * NOTE:
 * this should be used only when the event driven processing required.
 * If the method is executed within transaction, annotated with @Transactional, don't use it.
 *
 * @author: frank.li
 * @date: 2021-06-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventSponsor {
}
