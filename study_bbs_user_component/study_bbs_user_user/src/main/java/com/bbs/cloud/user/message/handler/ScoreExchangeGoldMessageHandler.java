package com.bbs.cloud.user.message.handler;

import com.bbs.cloud.common.enums.activity.ActivityTypeEnum;
import com.bbs.cloud.common.message.user.UserMessageDTO;
import com.bbs.cloud.common.message.user.dto.ScoreConvertMoneyMessage;
import com.bbs.cloud.common.message.user.enums.UserMessageTypeEnum;
import com.bbs.cloud.common.util.CommonUtil;
import com.bbs.cloud.common.util.JsonUtils;
import com.bbs.cloud.user.dto.BackpackDTO;
import com.bbs.cloud.user.dto.ScoreCardDTO;
import com.bbs.cloud.user.dto.ScoreConsumeRecordDTO;
import com.bbs.cloud.user.dto.UserLogRecordDTO;
import com.bbs.cloud.user.mapper.*;
import com.bbs.cloud.user.message.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 处理积分兑换金币产生的消息
 */
@Service
public class ScoreExchangeGoldMessageHandler implements MessageHandler {


    final static Logger logger = LoggerFactory.getLogger(RobLuckyBagMessageHandler.class);

    @Autowired
    private BackpackMapper backpackMapper;

    @Autowired
    private UserLogRecordMapper userLogRecordMapper;

    @Autowired
    private ScoreCardMapper scoreCardMapper;

    @Autowired
    private ScoreConsumeRecordMapper scoreConsumeRecordMapper;

    @Override
    public void handler(UserMessageDTO userMessageDTO) {
        logger.info("用户积分兑换金币产生的消息,message:{}", JsonUtils.objectToJson(userMessageDTO));
        try {
            String userId = userMessageDTO.getUserId();
            String message = userMessageDTO.getMessage();
            ScoreConvertMoneyMessage scoreConvertMoneyMessage = JsonUtils.jsonToPojo(message, ScoreConvertMoneyMessage.class);

            Integer gold = scoreConvertMoneyMessage.getGold();

            ScoreCardDTO scoreCardDTO = scoreCardMapper.queryScoreCardDTO(userId);
            scoreCardDTO.setScore(scoreCardDTO.getScore() - scoreConvertMoneyMessage.getConsumeScore());

            //第一步：更新用户背包的金币数量
            BackpackDTO backpackDTO = backpackMapper.queryBackpackDTO(userId);
            backpackDTO.setGold(backpackDTO.getGold() + gold);
            backpackMapper.updateBackpack(backpackDTO);

            //添加积分消费记录
            ScoreConsumeRecordDTO scoreConsumeRecordDTO = new ScoreConsumeRecordDTO();
            scoreConsumeRecordDTO.setId(CommonUtil.createUUID());
            scoreConsumeRecordDTO.setUserId(userId);
            scoreConsumeRecordDTO.setScoreConsume(scoreConvertMoneyMessage.getConsumeScore());
            scoreConsumeRecordDTO.setCreateDate(new Date());
            scoreConsumeRecordDTO.setType(ActivityTypeEnum.SCORECARD_GOLD.getType());
            scoreConsumeRecordDTO.setActivityId(scoreConvertMoneyMessage.getActivityId());
            scoreConsumeRecordDTO.setGold(gold);
            scoreConsumeRecordMapper.insertScoreConsumeRecord(scoreConsumeRecordDTO);

            //第三步：添加用户操作日志
            UserLogRecordDTO userLogRecordDTO = new UserLogRecordDTO(
                    userId,
                    "恭喜用户通过积分兑换金币活动，获得金币 ：" + gold);
            userLogRecordMapper.insertUserLogRecordDTO(userLogRecordDTO);

        } catch (Exception e) {
            logger.error("用户积分兑换金币产生的消息,发生异常,message:{}", JsonUtils.objectToJson(userMessageDTO), e);
            e.printStackTrace();
        }
    }

    @Override
    public String getHandlerType() {
        return UserMessageTypeEnum.BBS_CLOUD_USER_SCORE_CONVERT_MONEY.getType();
    }
}
