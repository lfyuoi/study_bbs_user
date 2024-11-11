package com.bbs.cloud.user.message.handler;

import com.bbs.cloud.common.contant.RabbitContant;
import com.bbs.cloud.common.contant.RedisContant;
import com.bbs.cloud.common.enums.gift.GiftEnum;
import com.bbs.cloud.common.message.user.UserMessageDTO;
import com.bbs.cloud.common.message.user.enums.UserMessageTypeEnum;
import com.bbs.cloud.common.util.CommonUtil;
import com.bbs.cloud.common.util.JedisUtil;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.user.contant.UserContant;
import com.bbs.cloud.user.dto.BackpackDTO;
import com.bbs.cloud.user.dto.BackpackGiftDTO;
import com.bbs.cloud.user.dto.ScoreCardDTO;
import com.bbs.cloud.user.mapper.BackpackGiftMapper;
import com.bbs.cloud.user.mapper.BackpackMapper;
import com.bbs.cloud.user.mapper.ScoreCardMapper;
import com.bbs.cloud.user.message.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserInfoCreateMessageHandler implements MessageHandler {

    final static Logger logger = LoggerFactory.getLogger(UserInfoCreateMessageHandler.class);

    @Autowired
    private BackpackGiftMapper backpackGiftMapper;

    @Autowired
    private BackpackMapper backpackMapper;

    @Autowired
    private ScoreCardMapper scoreCardMapper;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public void handler(UserMessageDTO userMessageDTO) {
        logger.info("接受用户注册请求,需要创建背包,背包礼物,积分卡的消息,消息内容：{}", JsonUtils.objectToJson(userMessageDTO));
        try {
            String userId = userMessageDTO.getUserId();
            BackpackDTO backpackDTO = new BackpackDTO();
            backpackDTO.setUserId(userId);
            backpackDTO.setId(CommonUtil.createUUID());
            backpackDTO.setGold(UserContant.DEFAULT_USER_BACKPACK_MONEY);
            backpackMapper.insertBackpack(backpackDTO);

            Map<Integer, GiftEnum> giftEnumMap = GiftEnum.getGiftsMap();
            for (Integer giftType : giftEnumMap.keySet()) {
                BackpackGiftDTO backpackGiftDTO = new BackpackGiftDTO();
                backpackGiftDTO.setId(CommonUtil.createUUID());
                backpackGiftDTO.setBackpackId(backpackDTO.getId());
                backpackGiftDTO.setGiftType(giftType);
                backpackGiftDTO.setAmount(UserContant.DEFAULT_USER_BACKPACK_GIFT_AMOUNT);
                backpackGiftMapper.insertBackpackGiftDTO(backpackGiftDTO);
                jedisUtil.set(RedisContant.getBbsCloudUserBackpackGiftKey(userId, giftType), String.valueOf(backpackGiftDTO.getAmount()));
            }
            ScoreCardDTO scoreCardDTO = new ScoreCardDTO();
            scoreCardDTO.setId(CommonUtil.createUUID());
            scoreCardDTO.setUserId(userId);
            scoreCardDTO.setScore(UserContant.DEFAULT_USER_SCORE);
            scoreCardMapper.insertScoreCard(scoreCardDTO);

            jedisUtil.set(RedisContant.BBS_CLOUD_USER_BACKPACK_KEY + userId,String.valueOf(backpackDTO.getGold()));
            jedisUtil.set(RedisContant.BBS_CLOUD_USER_SCORE_CARD_KEY + userId,String.valueOf(scoreCardDTO.getScore()));

        } catch (Exception e) {
            logger.info("接受用户注册请求,需要创建背包,背包礼物,积分卡的消息,发生异常：{}", JsonUtils.objectToJson(userMessageDTO));
            e.printStackTrace();
        }
    }

    @Override
    public String getHandlerType() {
        return UserMessageTypeEnum.BBS_CLOUD_USER_INFO_CREATE.getType();
    }
}
