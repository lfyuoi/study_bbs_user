package com.bbs.cloud.common.message.essay.dto;


import com.bbs.cloud.common.enums.essay.EntityTypeEnum;

public class PlayTourMessage {

    /**
     * 赠送人
     */
    private String sendUserId;

    /**
     * 礼物ID
     * {@link com.bbs.cloud.common.enums.gift.GiftEnum}
     */
    private Integer giftType;

    /**
     * 打赏内容的ID
     */
    private String entityId;

    /**
     * 打赏内容的类型
     * {@link EntityTypeEnum}
     */
    private Integer entityType;

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }
}
