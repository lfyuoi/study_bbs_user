package com.bbs.cloud.common.message.essay.enums;

public enum EssayMessageTypeEnum {

    BBS_CLOUD_ESSAY_LIKED("BBS_CLOUD_ESSAY_LIKED", "BBS_CLOUD_ESSAY_LIKED", "用户点赞产生的消息"),

    BBS_CLOUD_ESSAY_UNLIKED("BBS_CLOUD_ESSAY_UNLIKED", "BBS_CLOUD_ESSAY_UNLIKED", "用户取消点赞产生的消息"),

    BBS_CLOUD_ESSAY_COMMENT("BBS_CLOUD_ESSAY_COMMENT", "BBS_CLOUD_ESSAY_COMMENT", "用户评论产生的消息"),

    BBS_CLOUD_PLAY_TOUR("BBS_CLOUD_PLAY_TOUR", "BBS_CLOUD_PLAY_TOUR", "用户被打赏，产生的消息"),

    ;

    private String type;

    private String name;

    private String desc;

    private EssayMessageTypeEnum(String type, String name, String desc) {
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
