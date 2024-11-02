package com.bbs.cloud.common.model.essay;

import java.util.Date;

/**
 * 文章实体类
 */
public class EssayModel {

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     * {@link com.bbs.cloud.common.enums.essay.EssayStatusEnum}
     */
    private Integer status;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 删除时间
     */
    private Date deleteDate;

}
