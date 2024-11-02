package com.bbs.cloud.common.model.user;

import java.util.Date;

public class RechargeRecordModel {

    private String id;

    private String userId;

    private Integer gold;

    private Integer price;

    private String desc;

    /**
     * {@link com.bbs.cloud.common.enums.user.RechargeRecordStatusEnum}
     */
    private Integer status;

    private Date createDate;

    private Date updateDate;

    private Date finishDate;

    private Date cancelDate;

    private Date deleteDate;

}
