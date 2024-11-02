package com.bbs.cloud.common.model.essay;


import com.bbs.cloud.common.enums.essay.EntityTypeEnum;

import java.util.Date;

/**
 * 评论
 */
public class CommentModel {

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

}
