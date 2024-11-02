package com.bbs.cloud.common.enums.essay;

import java.util.HashMap;
import java.util.Map;

public enum EntityTypeEnum {

    ESSAY(1, "ESSAY", "帖子"),

    COMMENT(2, "COMMENT", "评论")

    ;

    private Integer type;

    private String name;

    private String desc;

    private EntityTypeEnum(Integer type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    public static Map<Integer, EntityTypeEnum> entityTypeEnumMap = new HashMap<>();

    static {
        for(EntityTypeEnum item : EntityTypeEnum.values()) {
            entityTypeEnumMap.put(item.getType(), item);
        }
    }

    public static EntityTypeEnum getEntityTypeEnumMap(Integer entityType) {
        return entityTypeEnumMap.getOrDefault(entityType, null);
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
