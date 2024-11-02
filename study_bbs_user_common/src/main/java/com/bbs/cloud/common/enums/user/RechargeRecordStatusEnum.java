package com.bbs.cloud.common.enums.user;

public enum RechargeRecordStatusEnum {

    INIT(1, "INIT", "初始化"),

    TO_BE_PAID(2, "TO_BE_PAID", "待支付"),

    FINISHED(3, "FINISHED", "已完成"),

    CANCEL(4, "CANCEL", "取消")

    ;

    private Integer status;

    private String name;

    private String desc;

    private RechargeRecordStatusEnum(Integer status, String name, String desc) {
        this.status = status;
        this.name = name;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
