package com.bbs.cloud.common.enums.essay;

public enum CommentStatusEnum {

    NORMAL(1, "NORMAL", "正常"),

    DEL(2, "DEL", "删除")

    ;

    private Integer status;

    private String name;

    private String desc;

    private CommentStatusEnum(Integer status, String name, String desc) {
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
