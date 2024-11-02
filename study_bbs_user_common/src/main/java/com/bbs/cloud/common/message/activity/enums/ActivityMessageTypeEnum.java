package com.bbs.cloud.common.message.activity.enums;

public enum ActivityMessageTypeEnum {

    BBS_CLOUD_USER_ROB_RED_PACKET("BBS_CLOUD_USER_ROB_RED_PACKET", "BBS_CLOUD_USER_ROB_RED_PACKET", "用户抢红包，生成的消息"),

    BBS_CLOUD_UPDATE_LUCKY_BAG("BBS_CLOUD_UPDATE_LUCKY_BAG", "BBS_CLOUD_UPDATE_LUCKY_BAG", "用户抢福袋，生成的消息"),

    BBS_CLOUD_USER_SCORE_CONVERT_GOLD("BBS_CLOUD_USER_SCORE_CONVERT_GOLD", "BBS_CLOUD_USER_SCORE_CONVERT_GOLD", "积分兑换金币，生成的消息"),
    BBS_CLOUD_USER_SCORE_CONVERT_LUCKY_BAG("BBS_CLOUD_USER_SCORE_CONVERT_LUCKY_BAG", "BBS_CLOUD_USER_SCORE_CONVERT_LUCKY_BAG", "积分兑换福袋，生成的消息"),

    ;

    private String type;

    private String name;

    private String desc;

    private ActivityMessageTypeEnum(String type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
