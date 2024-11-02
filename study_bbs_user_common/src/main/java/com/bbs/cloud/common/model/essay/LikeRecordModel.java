package com.bbs.cloud.common.model.essay;

import java.util.Date;

/**
 * 点赞记录
 */
public class LikeRecordModel {

    private String id;

    private String userId;

    /**
     * 点赞的ID
     */
    private String entityId;

    /**
     * 点赞的对象
     * {@link com.bbs.cloud.common.enums.essay.EntityTypeEnum}
     */
    private Integer entityType;

    /**
     * 是否删除
     * {@link com.bbs.cloud.common.enums.essay.LikeStatusEnum}
     */
    private Integer status;

    private Date createdDate;

    private Date updateDate;

}
