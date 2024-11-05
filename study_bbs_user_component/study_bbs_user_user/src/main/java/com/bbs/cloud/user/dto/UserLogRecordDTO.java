package com.bbs.cloud.user.dto;

import java.util.Date;

/**
 * 用户日志记录
 */
public class UserLogRecordDTO {

    private String id;

    private String userId;

    private Date createDate;

    public String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserLogRecordDTO(String id, String message) {
        this.id = id;
        this.message = message;
    }
}
