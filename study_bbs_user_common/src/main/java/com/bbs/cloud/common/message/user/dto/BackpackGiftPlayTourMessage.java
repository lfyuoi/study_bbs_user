package com.bbs.cloud.common.message.user.dto;

/**
 * 消耗背包礼物进行打赏
 */
public class BackpackGiftPlayTourMessage {

    private Integer giftType;
    private Integer giftNumber;

    public Integer getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(Integer giftNumber) {
        this.giftNumber = giftNumber;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }
}
