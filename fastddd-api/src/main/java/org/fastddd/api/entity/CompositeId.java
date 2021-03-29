package org.fastddd.api.entity;

import java.io.Serializable;

/**
 * Composite Id
 * @author: frank.li
 * @date: 2021/3/29
 */
public interface CompositeId extends Serializable {

    boolean isNewId();
}
