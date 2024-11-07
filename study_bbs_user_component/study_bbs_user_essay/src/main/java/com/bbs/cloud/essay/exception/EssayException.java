package com.bbs.cloud.essay.exception;


import com.bbs.cloud.common.error.ExceptionCode;

public enum EssayException implements ExceptionCode {

    EASSY_TITLE_IS_NOT_NULL(200001, "文章标题不能为空", "EASSY_TITLE_IS_NOT_NULL"),

    EASSY_CONTENT_IS_NOT_NULL(200002, "文章内容不能为空", "EASSY_CONTENT_IS_NOT_NULL"),

    ESSAY_ID_IS_NOT_NULL(200003, "文章ID不能为空", "ESSAY_ID_IS_NOT_NULL"),

    ESSAY_ID_FORMAT_NOT_TRUE(200004, "文章ID格式不正确", "ESSAY_ID_FORMAT_NOT_TRUE"),

    ENTITY_ID_IS_NOT_NULL(200005, "实体ID不能为空", "ENTITY_ID_IS_NOT_NULL"),

    ENTITY_ID_FORMAT_NOT_TRUE(200006, "实体ID格式不正确", "ENTITY_ID_FORMAT_NOT_TRUE"),

    ENTITY_TYPE_IS_NOT_EXIST(200007, "被评论的内容不存在", "ENTITY_TYPE_IS_NOT_EXIST"),

    COMMENT_CONTENT_IS_NOT_NULL(200008, "评论内容不能为空", "COMMENT_CONTENT_IS_NOT_NULL"),

    GIFT_TYPE_IS_NOT_EXIST(200009, "礼物类型不存在", "GIFT_TYPE_IS_NOT_EXIST"),

    REDIRECT_RECHARGE_VIEW(200010, "跳转到充值界面", "REDIRECT_RECHARGE_VIEW"),
    ENTITY_TYPE_IS_ERROR(2000011, "被评论的目标实体类型错误", " ENTITY_TYPE_IS_ERROR"),
    PLAY_TOUR_IS_NOT_EXIST(200007, "打赏内容不存在", "PLAY_TOUR_IS_NOT_EXIST"),


    ;

    private Integer code;

    private String message;

    private String name;

    private EssayException(Integer code, String message, String name) {
        this.code = code;
        this.message = message;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
