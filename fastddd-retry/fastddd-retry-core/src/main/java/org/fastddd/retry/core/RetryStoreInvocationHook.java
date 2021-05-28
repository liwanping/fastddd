package org.fastddd.retry.core;

import org.apache.commons.lang3.StringUtils;
import org.fastddd.api.retry.RetryMode;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.factory.BeanFactory;
import org.fastddd.common.factory.FactoryBuilder;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.invocation.InvocationHook;
import org.fastddd.retry.core.constants.RetryConstants;
import org.fastddd.retry.core.constants.RetryLauncher;
import org.fastddd.retry.core.factory.RetryTransactionFactory;
import org.fastddd.retry.core.model.RetryContext;
import org.fastddd.retry.core.model.RetryTransaction;
import org.fastddd.retry.core.service.RetryTransactionHelper;
import org.fastddd.retry.core.service.TransactionStoreService;
import org.fastddd.retry.core.utils.RetryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryStoreInvocationHook implements InvocationHook {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryStoreInvocationHook.class);

    @Override
    public boolean isQualified(Invocation invocation) {
        Retryable retryable = RetryUtils.getRetryable(invocation);
        return retryable != null && RetryMode.STORE == retryable.mode();
    }

    @Override
    public boolean beforeInvoke(Invocation invocation) {

        RetryContext retryContext = getRetryContext(invocation);
        if (retryContext == null) {
            //for the first invoking, init retry context
            retryContext = new RetryContext().setRetryLauncher(RetryLauncher.WORKFLOW);
            invocation.putContextValue(RetryConstants.RETRY_CONTEXT_KEY, retryContext);
            //save transaction
            RetryTransactionHelper.save(invocation);
        }
        return true;
    }

    @Override
    public void afterInvoke(Invocation invocation, Object result) {

        //remove transaction after success
        RetryTransactionHelper.remove(invocation);
    }

    @Override
    public void afterThrow(Invocation invocation, Throwable t) {

        RetryContext retryContext = getRetryContext(invocation);

        Retryable retryable = RetryUtils.getRetryable(invocation);

        //increase retried count
        int retriedCount = retryContext.getRetriedCount() + 1;

        if (RetryUtils.canRetry(retryable, t, retriedCount)) {
            //update retry context
            retryContext.setRetriedCount(retriedCount);
            retryContext.setRemark(t.getMessage());
            //update transaction for retrying
            RetryTransactionHelper.update(invocation);
        }
    }

    private RetryContext getRetryContext(Invocation invocation) {
        return invocation.getContextValue(RetryConstants.RETRY_CONTEXT_KEY, RetryContext.class);
    }
}
