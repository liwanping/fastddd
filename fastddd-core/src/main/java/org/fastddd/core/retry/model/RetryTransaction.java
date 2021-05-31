package org.fastddd.core.retry.model;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryTransaction {

    private String xid;

    private long branchId;

    private int retriedCount;

    private int maxAttempts;

    private String remark;

    private String applicationData;

    public String getXid() {
        return xid;
    }

    public RetryTransaction setXid(String xid) {
        this.xid = xid;
        return this;
    }

    public long getBranchId() {
        return branchId;
    }

    public RetryTransaction setBranchId(long branchId) {
        this.branchId = branchId;
        return this;
    }

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

    public String getApplicationData() {
        return applicationData;
    }

    public RetryTransaction setApplicationData(String applicationData) {
        this.applicationData = applicationData;
        return this;
    }
}
