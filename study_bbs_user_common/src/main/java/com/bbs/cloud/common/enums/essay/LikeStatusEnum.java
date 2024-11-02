package com.bbs.cloud.common.enums.essay;

public enum LikeStatusEnum {

    NORMAL(1, "NORMAL", "点赞"),

    DEL(2, "DEL", "取消点赞")

    ;

    private Integer status;

    private String name;

    private String desc;

    private LikeStatusEnum(Integer status, String name, String desc) {
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
