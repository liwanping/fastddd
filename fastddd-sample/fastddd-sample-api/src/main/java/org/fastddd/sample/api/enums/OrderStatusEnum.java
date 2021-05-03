package org.fastddd.sample.api.enums;

public enum OrderStatusEnum {
    CREATED(1, "创建"),
    PAID(2, "已付款"),
    FINISHED(3, "已完成"),
    CANCELED(4, "已取消");

    private Integer id;
    private String desc;

    OrderStatusEnum(Integer id, String desc) {
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
