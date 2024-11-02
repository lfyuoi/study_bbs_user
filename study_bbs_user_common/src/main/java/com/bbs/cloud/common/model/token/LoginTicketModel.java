package com.bbs.cloud.common.model.token;


import com.bbs.cloud.common.enums.user.LoginTicketStatusEnum;

import java.util.Date;

/**
 * 用户令牌
 */
public class LoginTicketModel {

    private String id;

    private String userId;
    /**
     * 令牌有效期
     */
    private Date expired;

    /**
     * {@link LoginTicketStatusEnum}
     */
    private Integer status;

    private String ticket;

}
