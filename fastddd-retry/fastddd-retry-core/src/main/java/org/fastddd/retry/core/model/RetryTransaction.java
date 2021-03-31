package org.fastddd.retry.core.model;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryTransaction {

    private int retryCount;

    private int retryLimit;

    private String remark;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryLimit() {
        return retryLimit;
    }

    public void setRetryLimit(int retryLimit) {
        this.retryLimit = retryLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
