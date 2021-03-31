package org.fastddd.retry.core.context;

import org.fastddd.common.invocation.Invocation;
import org.fastddd.retry.core.model.RetryTransaction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryContext {

    private static final ThreadLocal<RetryContext> TL = ThreadLocal.withInitial(RetryContext::new);

    private Map<Invocation, RetryTransaction> retryTransactionMap = new HashMap<>();

    public static RetryContext get() {
        return TL.get();
    }

    public static void remove() {
        TL.remove();
    }

    public RetryTransaction getTransaction(Invocation invocation) {
        return retryTransactionMap.get(invocation);
    }

    public void putTransaction(Invocation invocation, RetryTransaction retryTransaction) {
        retryTransactionMap.put(invocation, retryTransaction);
    }

    public void removeTransaction(Invocation invocation) {
        if (retryTransactionMap != null) {
            retryTransactionMap.remove(invocation);
        }
    }
}
