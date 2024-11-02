package com.bbs.cloud.common.enums.user;

public enum LoginTicketStatusEnum {

    VALID(0, "VALID", "有效"),

    INVALID(1, "INVALID", "无效")

    ;

    private Integer status;

    private String name;

    private String desc;

    private LoginTicketStatusEnum(Integer status, String name, String desc) {
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
