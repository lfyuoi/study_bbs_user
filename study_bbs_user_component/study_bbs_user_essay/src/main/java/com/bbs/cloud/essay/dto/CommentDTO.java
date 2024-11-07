package com.bbs.cloud.essay.dto;


import com.bbs.cloud.common.enums.essay.EntityTypeEnum;

import java.util.Date;

/**
 * 评论
 */
public class CommentDTO {

    private String id;

    private String essayId;

    private String userId;

    /**
     * 评论的ID
     */
    private String entityId;

    /**
     * 评论的对象
     * {@link EntityTypeEnum}
     */
    private Integer entityType;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 是否删除
     * {@link com.bbs.cloud.common.enums.essay.CommentStatusEnum}
     */
    private Integer status;

    private Date createDate;

    private Date updateDate;

    private Date deleteDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEssayId() {
        return essayId;
    }

    public void setEssayId(String essayId) {
        this.essayId = essayId;
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

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}
