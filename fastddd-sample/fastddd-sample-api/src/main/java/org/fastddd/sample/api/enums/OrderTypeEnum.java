package org.fastddd.sample.api.enums;

public enum  OrderTypeEnum {
    NORMAL(1, "普通订单"),
    GROUPON(2, "拼团订单"),
    DEPOSIT(3, "预付订单");

    private Integer id;
    private String desc;

    OrderTypeEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
