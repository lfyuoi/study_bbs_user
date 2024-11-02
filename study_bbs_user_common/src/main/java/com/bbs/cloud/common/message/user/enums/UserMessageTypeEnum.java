package com.bbs.cloud.common.message.user.enums;

public enum UserMessageTypeEnum {

    BBS_CLOUD_USER_INFO_CREATE("BBS_CLOUD_USER_INFO_CREATE", "BBS_CLOUD_USER_INFO_CREATE", "用户注册成功，需要创建背包，积分卡信息"),
    BBS_CLOUD_USER_INFO_INIT("BBS_CLOUD_USER_INFO_INIT", "BBS_CLOUD_USER_INFO_INIT", "用户登录成功，需要缓存背包，积分卡信息"),
    BBS_CLOUD_USER_INFO_Delete("BBS_CLOUD_USER_INFO_Delete", "BBS_CLOUD_USER_INFO_Delete", "用户退出登录，需要删除背包，积分卡信息"),

    BBS_CLOUD_USER_ROB_RED_PACKET("BBS_CLOUD_USER_ROB_RED_PACKET", "BBS_CLOUD_USER_ROB_RED_PACKET", "用户抢红包，生成的消息"),

    BBS_CLOUD_USER_ROB_LUCKY_BAG("BBS_CLOUD_USER_ROB_LUCKY_BAG", "BBS_CLOUD_USER_ROB_LUCKY_BAG", "用户抢福袋，生成的消息"),

    BBS_CLOUD_USER_SCORE_CONVERT_LUCKY_BAG("BBS_CLOUD_USER_SCORE_CONVERT_LUCKY_BAG", "BBS_CLOUD_USER_SCORE_CONVERT_LUCKY_BAG", "积分兑换福袋，生成的消息"),

    BBS_CLOUD_USER_SCORE_CONVERT_MONEY("BBS_CLOUD_USER_SCORE_CONVERT_MONEY", "BBS_CLOUD_USER_SCORE_CONVERT_MONEY", "积分兑换金币，生成的消息"),

    BBS_CLOUD_USER_ADD_ESSAY("BBS_CLOUD_USER_ADD_ESSAY", "BBS_CLOUD_USER_ADD_ESSAY", "用户发布文章, 需要增加用户积分"),

    BBS_CLOUD_USER_ADD_COMMENT("BBS_CLOUD_USER_ADD_COMMENT", "BBS_CLOUD_USER_ADD_COMMENT", "用户评论, 需要增加用户积分"),

    BBS_CLOUD_USER_LIKED("BBS_CLOUD_USER_LIKED", "BBS_CLOUD_USER_LIKED", "用户被点赞, 需要增加用户积分"),

    BBS_CLOUD_BACKPACK_GIFT_PLAY_TOUR("BBS_CLOUD_BACKPACK_GIFT_PLAY_TOUR", "BBS_CLOUD_BACKPACK_GIFT_PLAY_TOUR", "通过背包礼物进行打赏"),

    BBS_CLOUD_BACKPACK_GOLD_PLAY_TOUR("BBS_CLOUD_BACKPACK_GOLD_PLAY_TOUR", "BBS_CLOUD_BACKPACK_GOLD_PLAY_TOUR", "通过背包金币进行打赏"),

    ;

    private String type;

    private String name;

    private String desc;

    private UserMessageTypeEnum(String type, String name, String desc) {
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
