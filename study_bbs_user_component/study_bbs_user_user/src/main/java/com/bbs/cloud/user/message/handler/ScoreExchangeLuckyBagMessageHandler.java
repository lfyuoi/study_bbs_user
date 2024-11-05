package com.bbs.cloud.user.message.handler;

import com.bbs.cloud.common.enums.gift.GiftEnum;
import com.bbs.cloud.common.message.user.UserMessageDTO;
import com.bbs.cloud.common.message.user.dto.RobLuckyBagMessage;
import com.bbs.cloud.common.message.user.dto.ScoreConvertLuckyBagMessage;
import com.bbs.cloud.common.message.user.enums.UserMessageTypeEnum;
import com.bbs.cloud.common.util.CommonUtil;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.user.contant.UserContant;
import com.bbs.cloud.user.dto.*;
import com.bbs.cloud.user.mapper.*;
import com.bbs.cloud.user.message.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScoreExchangeLuckyBagMessageHandler implements MessageHandler {


    final static Logger logger = LoggerFactory.getLogger(RobLuckyBagMessageHandler.class);

    @Autowired
    private BackpackMapper backpackMapper;

    @Autowired
    private BackpackGiftMapper backpackGiftMapper;

    @Autowired
    private UserLogRecordMapper userLogRecordMapper;

    @Autowired
    private LuckyBagRecordMapper luckyBagRecordMapper;

    @Autowired
    private ScoreCardMapper scoreCardMapper;

    @Override
    public void handler(UserMessageDTO userMessageDTO) {
        logger.info("用户积分兑换福袋产生的消息,message:{}", JsonUtils.objectToJson(userMessageDTO));
        try {
            String userId = userMessageDTO.getUserId();
            String message = userMessageDTO.getMessage();
            ScoreConvertLuckyBagMessage scoreConvertLuckyBagMessage = JsonUtils.jsonToPojo(message, ScoreConvertLuckyBagMessage.class);

            Integer giftType = scoreConvertLuckyBagMessage.getGiftType();

            ScoreCardDTO scoreCardDTO = scoreCardMapper.queryScoreCardDTO(userId);
            scoreCardDTO.setScore(scoreCardDTO.getScore() - scoreConvertLuckyBagMessage.getConsumeScore());

            //第一步：更新用户背包的礼物列表
            BackpackDTO backpackDTO = backpackMapper.queryBackpackDTO(userId);
            BackpackGiftDTO backpackGiftDTO = backpackGiftMapper.queryBackpackGiftDTO(backpackDTO.getId(), giftType);
            backpackGiftDTO.setAmount(backpackGiftDTO.getAmount() + UserContant.DEFAULT_ROB_LUCKY_GIFT_AMOUNT);
            backpackGiftMapper.updateBackpackGift(backpackGiftDTO);

            //第二步：添加用户领取福袋记录
            LuckyBagRecordDTO luckyBagRecordDTO = new LuckyBagRecordDTO();
            luckyBagRecordDTO.setId(CommonUtil.createUUID());
            luckyBagRecordDTO.setCreateDate(new Date());
            luckyBagRecordDTO.setUserId(userId);
            luckyBagRecordDTO.setGiftType(giftType);
            luckyBagRecordDTO.setActivityId(scoreConvertLuckyBagMessage.getActivityId());
            luckyBagRecordDTO.setLuckyBagId(scoreConvertLuckyBagMessage.getLuckyBagId());
            luckyBagRecordMapper.insertLuckyBagRecordDTO(luckyBagRecordDTO);

            //第三步：添加用户操作日志
            UserLogRecordDTO userLogRecordDTO = new UserLogRecordDTO(
                    userId,
                    "恭喜用户通过积分兑换福袋活动，获得一个礼物 ：" + GiftEnum.getGiftsMap().get(giftType).getDesc());
            userLogRecordMapper.insertUserLogRecordDTO(userLogRecordDTO);

        } catch (Exception e) {
            logger.error("用户积分兑换福袋产生的消息,发生异常,message:{}", JsonUtils.objectToJson(userMessageDTO), e);
            e.printStackTrace();
        }
    }

    @Override
    public String gerHandlerType() {
        return UserMessageTypeEnum.BBS_CLOUD_USER_SCORE_CONVERT_LUCKY_BAG.getType();
    }
}
