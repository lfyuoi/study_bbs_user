package com.bbs.cloud.common.message.user.dto;

public class LikedMessage {
    /**
     * 点赞的ID
     */
    private String entityId;

    /**
     * 点赞的对象
     * {@link com.bbs.cloud.common.enums.essay.EntityTypeEnum}
     */
    private int entityType;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }
}
