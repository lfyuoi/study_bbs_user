package com.bbs.cloud.common.contant;

public class RedisContant {

    /**
     * 用来存放福袋活动中的福袋
     */
    public static final String BBS_CLOUD_ACTIVITY_LUCKY_BAG_LIST = "bbs:cloud:activity:lucky:bag:list";

    /**
     * 记录用户抢福袋记录
     */
    public static final String BBS_CLOUD_ACTIVITY_LUCKY_BAG_GET_RECORD_SET = "bbs:cloud:activity:lucky:bag:get:record:set";

    /**
     * 用来存放红包活动中的红包
     */
    public static final String BBS_CLOUD_ACTIVITY_RED_PACKET_LIST = "bbs:cloud:activity:red:packet:list";

    /**
     * 记录用户抢红包记录
     */
    public static final String BBS_CLOUD_ACTIVITY_RED_PACKET_GET_RECORD_SET = "bbs:cloud:activity:red:packet:get:record:set";

    /**
     * 用来存放积分兑换福袋活动中的福袋
     */
    public static final String BBS_CLOUD_ACTIVITY_SCORE_LUCKY_BAG_LIST = "bbs:cloud:activity:score:lucky:bag:list";

    /**
     * 积分兑换金币活动中的未使用金币
     */
    public static final String BBS_CLOUD_ACTIVITY_SCORE_GOLD = "bbs:cloud:activity:score:lucky:gold";

    /**
     * 积分兑换金币活动，需要给金币加锁
     */
    public static final String BBS_CLOUD_ACTIVITY_SCORE_GOLD_LOCK = "bbs:cloud:activity:score:lucky:gold:lock";

    /**
     * 用户会话key
     */
    public static final String BBS_CLOUD_USER_TICKET_KEY = "bbs:cloud:user:ticket:key:";

    /**
     * 用户背包KEY
     */
    public static final String BBS_CLOUD_USER_BACKPACK_KEY = "bbs:cloud:user:backpack:key:";

    public static final String BBS_CLOUD_USER_BACKPACK_LOCK_KEY = "bbs:cloud:user:backpack:lock:key:";

    /**
     * 用户背包礼物KEY
     * @param userId
     * @param giftType
     * @return
     */
    public static String getBbsCloudUserBackpackGiftKey(String userId, Integer giftType) {
        return "bbs:cloud:user:backpack:gift:key:" + userId + ":gift:" + giftType;
    }
    public static String getUserBagHashKey (String userId){
        return "bbs:cloud:user:backpack:key:"+userId;
    }
    public static final String BBS_CLOUD_USER_BACKPACK_GOLD = "GOLD";

    public static final String BBS_CLOUD_USER_BACKPACK_GIFT = "GIFT";


    public static String getBbsCloudUserBackpackGiftLockKey(String userId, Integer giftType) {
        return "bbs:cloud:user:backpack:gift:lock:key:" + userId + ":gift:" + giftType;
    }

    /**
     * 用户积分卡KEY
     */
    public static final String BBS_CLOUD_USER_SCORE_CARD_KEY = "bbs:cloud:user:score:card:key:";

    /**
     *  用户积分锁
     */
    public static final String BBS_CLOUD_USER_SCORE_CARD_LOCK = "bbs:cloud:user:score:card:lock:";


    public static final String BBS_CLOUD_LIKE_SET_KEY = "bbs:cloud:user:score:card:key:";


    /**
     * 默认redis是否操作的标志 0-操作失败
     */
    public static final Long DEFAULT_REDIS_OPERATE_FLAG = 0L;

    /**
     * 存放用户产生的消息
     */
    public static final String BBS_CLOUD_USER_MESSAGE_LIST = "bbs:cloud:user:message:list";
    /**
     * 存放文章产生的消息
     */
    public static final String BBS_CLOUD_ESSAY_MESSAGE_LIST = "bbs:cloud:essay:message:list";
    /**
     * 存放用户的文章的消息
     */
    public static final String BBS_CLOUD_USER_ESSAY_MESSAGE_LIST = "bbs:cloud:essay:message:list";

    /**
     * 存放用户的点赞记录
     */
    public static final String BBS_CLOUD_LIKE_MESSAGE_LIST = "bbs:cloud:essay:like:message:list";

    /**
     * 存放用户的打赏记录
     */
    public static final String BBS_CLOUD_PLAY_TOUR_MESSAGE_LIST = "bbs:cloud:essay:play:message:list";
}
