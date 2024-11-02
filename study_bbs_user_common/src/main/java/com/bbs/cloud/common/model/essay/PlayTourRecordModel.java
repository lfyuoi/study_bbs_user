package com.bbs.cloud.common.model.essay;


import com.bbs.cloud.common.enums.essay.EntityTypeEnum;

import java.util.Date;

/**
 * 打赏记录
 */
public class PlayTourRecordModel {

    private String id;

    /**
     * 赠送人
     */
    private String sendUserId;

    /**
     * 接受者
     */
    private String takeUserId;

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

    private Date createDate;

}
