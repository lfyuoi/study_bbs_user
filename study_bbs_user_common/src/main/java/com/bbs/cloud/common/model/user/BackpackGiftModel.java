package com.bbs.cloud.common.model.user;

/**
 * 背包礼物
 */
public class BackpackGiftModel {

    private String id;

    /**
     * 所属背包ID
     */
    private String backpackId;

    /**
     * 礼物类型
     * {@link com.bbs.cloud.common.enums.gift.GiftEnum}
     */
    private Integer giftType;

    /**
     * 礼物数量
     */
    private Integer amount;


}
