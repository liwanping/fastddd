package org.fastddd.retry.core.model;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryTransaction {

    private int retriedCount;

    private int maxAttempts;

    private String remark;

    public int getRetriedCount() {
        return retriedCount;
    }

    public void setRetriedCount(int retriedCount) {
        this.retriedCount = retriedCount;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
