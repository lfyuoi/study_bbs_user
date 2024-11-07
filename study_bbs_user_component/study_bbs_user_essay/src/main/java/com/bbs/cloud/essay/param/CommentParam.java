package com.bbs.cloud.essay.param;


import com.bbs.cloud.common.enums.essay.EntityTypeEnum;

import java.util.Date;

/**
 * 评论
 */
public class CommentParam {

    private String essayId;

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

    public String getEssayId() {
        return essayId;
    }

    public void setEssayId(String essayId) {
        this.essayId = essayId;
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
}
