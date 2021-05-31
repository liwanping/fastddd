package org.fastddd.core.session.transaction;

import org.fastddd.common.id.IdUtils;
import org.fastddd.common.id.XidUtils;

/**
 * @author: frank.li
 * @date: 2021-05-31
 */
public class TransactionalXidBuilder {

    public static TransactionalXid generate() {
        long transactionId = IdUtils.generateId();
        String xid = XidUtils.generateXID(transactionId);
        return new TransactionalXid(xid, transactionId);
    }

}
