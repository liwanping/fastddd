package org.fastddd.core.session.transaction;

/**
 * @author: frank.li
 * @date: 2021-05-31
 */
public class TransactionalXid {

    private final String xid;

    private final long transactionId;

    public TransactionalXid(String xid, long transactionId) {
        this.xid = xid;
        this.transactionId = transactionId;
    }

    public String getXid() {
        return xid;
    }

    public long getTransactionId() {
        return transactionId;
    }
}
