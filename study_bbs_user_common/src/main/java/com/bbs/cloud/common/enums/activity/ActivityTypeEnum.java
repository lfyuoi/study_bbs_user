package com.bbs.cloud.common.enums.activity;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动类型枚举
 */
public enum ActivityTypeEnum {

    LUCKY_BAG(1, "LUCKY_BAG", "福袋活动"),

    RED_PACKET(2, "RED_PACKET", "红包活动"),

    SCORECARD_LUCKY_BAG(3, "SCORECARD_LUCKY_BAG", "积分兑换福袋活动"),

    SCORECARD_GOLD(4, "SCORECARD_GOLD", "积分兑换金币活动")

    ;

    private Integer type;

    private String name;

    private String desc;

    private ActivityTypeEnum(Integer type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    private static Map<Integer, ActivityTypeEnum> activityTypeMap = new HashMap<>();

    static {
        for(ActivityTypeEnum item : ActivityTypeEnum.values()) {
            activityTypeMap.put(item.getType(), item);
        }
    }

    public static Map<Integer, ActivityTypeEnum> getActivityTypeMap() {
        return activityTypeMap;
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
