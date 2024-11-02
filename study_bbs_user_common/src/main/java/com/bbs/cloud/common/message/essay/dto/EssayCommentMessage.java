package com.bbs.cloud.common.message.essay.dto;

import java.util.Date;

public class EssayCommentMessage {

    private String id;
    private String type;
    private String userId;
    private String content;
    private String entityId;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


}
