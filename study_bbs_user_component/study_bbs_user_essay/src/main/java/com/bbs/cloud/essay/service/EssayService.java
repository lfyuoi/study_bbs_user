package com.bbs.cloud.essay.service;

import com.bbs.cloud.common.contant.RedisContant;
import com.bbs.cloud.common.enums.essay.EntityTypeEnum;
import com.bbs.cloud.common.enums.essay.EssayStatusEnum;
import com.bbs.cloud.common.enums.gift.GiftEnum;
import com.bbs.cloud.common.enums.user.ScoreRuleEnum;
import com.bbs.cloud.common.local.HostHolder;
import com.bbs.cloud.common.result.HttpResult;
import com.bbs.cloud.common.util.CommonUtil;
import com.bbs.cloud.common.util.JedisUtil;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.common.util.RedisLockHelper;
import com.bbs.cloud.common.vo.UserVO;
import com.bbs.cloud.essay.contant.EssayContant;
import com.bbs.cloud.essay.dto.CommentDTO;
import com.bbs.cloud.essay.dto.EssayDTO;
import com.bbs.cloud.essay.dto.EssayTopicDTO;
import com.bbs.cloud.essay.exception.EssayException;
import com.bbs.cloud.essay.mapper.CommentMapper;
import com.bbs.cloud.essay.mapper.EssayMapper;
import com.bbs.cloud.essay.mapper.EssayTopicMapper;
import com.bbs.cloud.essay.param.CommentParam;
import com.bbs.cloud.essay.param.EssayPublishParam;
import com.bbs.cloud.essay.param.LikeParam;
import com.bbs.cloud.essay.param.PlayTourParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EssayService {

    final static Logger logger = LoggerFactory.getLogger(EssayService.class);

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EssayMapper essayMapper;

    @Autowired
    private EssayTopicMapper essayTopicMapper;

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisLockHelper redisLockHelper;

    public HttpResult publish(EssayPublishParam param) {
        UserVO userVO = hostHolder.getUser();
        String userId = userVO.getId();
        logger.info("发布文章，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));

        String title = param.getTitle();
        if (StringUtils.isEmpty(title)) {
            logger.error("发布文章，文章标题不能为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.EASSY_TITLE_IS_NOT_NULL);
        }

        String content = param.getContent();
        if (StringUtils.isEmpty(content)) {
            logger.error("发布文章，文章内容不能为空,请求参数：{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.EASSY_CONTENT_IS_NOT_NULL);
        }

        List<String> topics = param.getTopics();

        EssayDTO essayDTO = new EssayDTO();
        essayDTO.setId(CommonUtil.createUUID());
        essayDTO.setUserId(userId);
        essayDTO.setTitle(title);
        essayDTO.setContent(content);
        essayDTO.setStatus(EssayStatusEnum.NORMAL.getStatus());
        essayDTO.setCreateDate(new Date());
        essayDTO.setCommentCount(EssayContant.DEFAULT_COMMENT_COUNT);
        essayDTO.setLikeCount(EssayContant.DEFAULT_LIKED_COUNT);
        essayDTO.setUpdateDate(new Date());
        essayMapper.insertEssayDTO(essayDTO);

        List<EssayTopicDTO> essayTopicDTOS = new ArrayList<>();
        for (String topic : topics) {
            EssayTopicDTO essayTopicDTO = new EssayTopicDTO();
            essayTopicDTO.setId(CommonUtil.createUUID());
            essayTopicDTO.setEssayId(essayDTO.getId());
            essayTopicDTO.setRule(topic);
            essayTopicDTOS.add(essayTopicDTO);
        }
        if (essayTopicDTOS.size() > 0) {
            essayTopicMapper.insertEssayTopicDTO(essayTopicDTOS);
        }

        jedisUtil.incrBy(RedisContant.BBS_CLOUD_USER_SCORE_CARD_KEY + userId, Long.valueOf(ScoreRuleEnum.ESSAY.getScore()));

        /**
         * TODO : 通过MQ异步的添加用户积分,自己做
         */


        return null;
    }

    public HttpResult addComment(CommentParam param) {
        UserVO userVO = hostHolder.getUser();
        String userId = userVO.getId();
        logger.info("发布评论，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));

        String essayId = param.getEssayId();
        if (StringUtils.isEmpty(essayId)) {
            logger.error("发布评论，文章ID不能为空，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.ESSAY_ID_IS_NOT_NULL);
        }
        if (essayId.length() != 32) {
            logger.error("发布评论，文章ID格式不正确，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.ESSAY_ID_FORMAT_NOT_TRUE);
        }

        EssayDTO essayDTO = essayMapper.queryEssayDTO(essayId);
        if (essayDTO == null) {
            logger.info("发布评论，文章不存在，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.ESSAY_ID_IS_NOT_NULL);
        }

        String entityId = param.getEntityId();
        if (StringUtils.isEmpty(entityId)) {
            logger.error("发布评论，评论ID不能为空，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.ENTITY_ID_IS_NOT_NULL);
        }

        String content = param.getContent();
        if (StringUtils.isEmpty(content)) {
            logger.error("发布评论，评论内容不能为空，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.COMMENT_CONTENT_IS_NOT_NULL);
        }

        Integer entityType = param.getEntityType();
        if (ObjectUtils.isEmpty(entityType)) {
            logger.error("发布评论，评论类型不能为空，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.ENTITY_TYPE_IS_NOT_EXIST);
        }
        /**
         * TODO 二次评论，需要判断评论是否存在
         * （思考 感觉要在commentMapper中新建一个sql 根据 userId和文章ID essayId 查询该文章下是否有评论 ，如果有则为重复评论）
         */
//        if (entityType == EntityTypeEnum.COMMENT.getType() && essayDTO.getContent()){
//            logger.info("发布评论，文章已删除，userId:{},请求参数：{}",userId, JsonUtils.objectToJson(param));
//            return HttpResult.generateHttpResult(EssayException.ESSAY_IS_DEL);
//        }

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(CommonUtil.createUUID());
        commentDTO.setUserId(userId);
        commentDTO.setEssayId(essayId);
        commentDTO.setEntityId(entityId);
        commentDTO.setContent(content);
        commentDTO.setStatus(EssayStatusEnum.NORMAL.getStatus());
        commentDTO.setEntityType(entityType);
        commentDTO.setCreateDate(new Date());
        commentDTO.setUpdateDate(new Date());
        commentMapper.insertCommentDOT(commentDTO);

        /**
         * TODO 评论后，1.添加文章的评论数量。2.添加用户积分 3.通知被评论的人
         */
        return null;
    }

    public HttpResult liked(LikeParam param) {
        UserVO userVO = hostHolder.getUser();
        String userId = userVO.getId();
        logger.info("点赞，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));

        String entityId = param.getEntityId();
        if (StringUtils.isEmpty(entityId)) {
            logger.error("点赞，评论ID不能为空，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.ESSAY_ID_IS_NOT_NULL);
        }
        Long flag = jedisUtil.sadd(RedisContant.BBS_CLOUD_LIKE_SET_KEY + userId, userId);
        if (flag == RedisContant.DEFAULT_REDIS_OPERATE_FLAG) {
            logger.error("点赞，重复点赞，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.ok();
        }
        // TODO 通过MQ去处理点赞产生的消息 1.被点赞的人积分增加；2.增加点赞记录；3.增加文章点赞数量

        return HttpResult.ok();
    }

    public HttpResult unliked(LikeParam param) {
        UserVO userVO = hostHolder.getUser();
        String userId = userVO.getId();
        logger.info("取消点赞，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));

        String entityId = param.getEntityId();
        if (StringUtils.isEmpty(entityId)) {
            logger.error("取消点赞，评论ID不能为空，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.ESSAY_ID_IS_NOT_NULL);
        }
        Long flag = jedisUtil.srem(RedisContant.BBS_CLOUD_LIKE_SET_KEY + userId, userId);
        if (flag == RedisContant.DEFAULT_REDIS_OPERATE_FLAG) {
            logger.error("取消点赞，重复取消点赞，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.ok();
        }
        // TODO 通过MQ去处理点赞产生的消息 1....；2.删除点赞记录；3.减少文章点赞数量

        return HttpResult.ok();
    }

    public HttpResult playTour(PlayTourParam param) {
        UserVO userVO = hostHolder.getUser();
        String userId = userVO.getId();
        logger.info("开始打赏，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));

        // 验证要打赏的内容是否存在
        Integer entityType = param.getEntityType();
        String entityId = param.getEntityId();
        if (!checkEntityType(entityType, entityId)) {
            logger.error("打赏，要打赏的内容不存在，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.PLAY_TOUR_IS_NOT_EXIST);
        }
        Integer giftType = param.getGiftType();
        if (GiftEnum.getGiftsMap().getOrDefault(giftType, null) == null) {
            logger.error("打赏，礼物类型不存在，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(EssayException.GIFT_TYPE_IS_NOT_EXIST);
        }

        //要打赏的礼物是否有库存
        boolean flag = false;
        String key = RedisContant.getBbsCloudUserBackpackGiftLockKey(userId, giftType);
        try {
            if (redisLockHelper.lock(key,CommonUtil.createUUID(),3000l)){
                Integer amount = jedisUtil.get(RedisContant.getBbsCloudUserBackpackGiftKey(userId, giftType), Integer.class);
                if (amount > EssayContant.DEFAULT_PLAY_TOUR) {
                    jedisUtil.decr(RedisContant.getBbsCloudUserBackpackGiftKey(userId, giftType));
                    flag = true;

                    /**
                     * TODO 通过MQ,添加打赏记录，扣减DB的数据库礼物列表
                     */


                    return HttpResult.ok();
                }
            }
        }catch (Exception e){
            logger.info("打赏，打赏异常-通过礼物打赏，礼物库存不足，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            if (flag){
                jedisUtil.decr(RedisContant.getBbsCloudUserBackpackGiftKey(userId, giftType));
            }
            e.printStackTrace();
            return HttpResult.fail();
        }finally {
            redisLockHelper.releaseLock(key);
        }

        //用户的金币是否满足
        boolean backpackFlag = false;
        Integer giftPrice = GiftEnum.getGiftsMap().get(giftType).getPrice();
        String backpackLock = RedisContant.BBS_CLOUD_USER_BACKPACK_LOCK_KEY +userId;
        try {
            if (redisLockHelper.lock(backpackLock, CommonUtil.createUUID(), 3000l)) {
                Integer gold = jedisUtil.get(RedisContant.BBS_CLOUD_USER_BACKPACK_LOCK_KEY +userId, Integer.class);
                if (gold > giftPrice){
                    jedisUtil.decrBy(RedisContant.BBS_CLOUD_USER_BACKPACK_LOCK_KEY +userId, Long.valueOf(giftPrice));
                    backpackFlag = true;
                    /**
                     * TODO 通过MQ,添加打赏记录，扣减DB的数据库用户的背包金币
                     */

                    return HttpResult.ok();
                }
            }
        }catch (Exception e){
            logger.info("打赏，打赏异常-通过金币打赏，金币不足，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            if (backpackFlag){
                jedisUtil.incrBy(RedisContant.BBS_CLOUD_USER_BACKPACK_LOCK_KEY +userId, Long.valueOf(giftPrice));
            }
            e.printStackTrace();
            return HttpResult.fail();

        }finally {
            redisLockHelper.releaseLock(backpackLock);
        }

        //跳转到充值界面
        return HttpResult.generateHttpResult(EssayException.REDIRECT_RECHARGE_VIEW);
    }

    /**
     * 判断打赏内容是否存在
     *
     * @param entityType
     * @param entityId
     * @return
     */
    private boolean checkEntityType(Integer entityType, String entityId) {
        if (entityType.equals(EntityTypeEnum.ESSAY.getType())) {
            EssayDTO essayDTO = essayMapper.queryEssayDTO(entityId);
            if (essayDTO == null) {
                return false;
            }
            return true;
        }
        if (entityType.equals(EntityTypeEnum.COMMENT.getType())) {
            CommentDTO commentDTO = commentMapper.queryCommentDTO(entityId);
            if (commentDTO == null) {
                return false;
            }
            return true;
        }
        return false;
    }
}
