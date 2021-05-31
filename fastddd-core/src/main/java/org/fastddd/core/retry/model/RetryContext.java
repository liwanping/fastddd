package org.fastddd.core.retry.model;


import org.fastddd.core.retry.constants.RetryLauncher;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryContext {

    private int retriedCount;

    private int maxAttempts;

    private String remark;

    private RetryLauncher retryLauncher;

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public RetryContext setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public RetryContext setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public RetryLauncher getRetryLauncher() {
        return retryLauncher;
    }

    public RetryContext setRetryLauncher(RetryLauncher retryLauncher) {
        this.retryLauncher = retryLauncher;
        return this;
    }

    public int getRetriedCount() {
        return retriedCount;
    }

    public RetryContext setRetriedCount(int retriedCount) {
        this.retriedCount = retriedCount;
        return this;
    }
}
