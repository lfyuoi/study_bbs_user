package com.bbs.cloud.user.message;

import com.bbs.cloud.common.contant.RabbitContant;
import com.bbs.cloud.common.contant.RedisContant;
import com.bbs.cloud.common.message.user.UserMessageDTO;
import com.bbs.cloud.common.util.JedisUtil;
import com.bbs.cloud.common.util.JsonUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MessageReceiver implements ApplicationRunner {

    final static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    @Autowired
    private List<MessageHandler> messageHandlers;

    @Autowired
    private JedisUtil jedisUtil;

    private ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("essay--%d")
            .build();
    //信号量
    private Semaphore semaphore = new Semaphore(50);

    //线程池去处理
    ThreadPoolExecutor executorPool = new ThreadPoolExecutor(
            10,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(50),
            threadFactory);

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    @RabbitListener(queues = RabbitContant.USER_QUEUE_NAME)
    public void receiver(String message) {
        logger.info("接收到用户产生的消息：{}", message);
        if (StringUtils.isEmpty(message)) {
            logger.info("接收到用户产生的消息，消息为空:{}", message);
            return;
        }
        UserMessageDTO userMessageDTO;
        try {
            userMessageDTO = JsonUtils.jsonToPojo(message, UserMessageDTO.class);
            if (userMessageDTO == null) {
                logger.info("接收到用户产生的消息，消息转换为空:{}", message);
                return;
            }
            ;
        } catch (Exception e) {
            logger.info("接收到用户产生的消息，消息转换异常:{}", message);
            e.printStackTrace();
            return;
        }
        String messageDTOType = userMessageDTO.getType();

        messageHandlers.stream()
                .filter(item -> item.gerHandlerType().equals(messageDTOType))
                .findFirst().get()
                .handler(userMessageDTO);

        jedisUtil.lpush(RedisContant.BBS_CLOUD_ESSAY_MESSAGE_LIST, message);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread thread = new Thread(() -> {
            dealRedisMessage();
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 大家要做一个守护线程
     * 1、统计线程池执行任务的线程数量
     * 2、如果线程数量小于50，我们就需要从redis列表中获取数据
     */
    public void dealRedisMessage() {
        while (true) {

            if (jedisUtil.llen(RedisContant.BBS_CLOUD_ESSAY_MESSAGE_LIST) < 1) {
                continue;
            }
            if (atomicInteger.get() < 50) {
                String message = jedisUtil.lpop(RedisContant.BBS_CLOUD_ESSAY_MESSAGE_LIST);

                UserMessageDTO essayMessageDTO = null;

                if (StringUtils.isEmpty(message)) {
                    logger.info("从redis中获取消息-essay消息为空, message:{}", message);
                    continue;
                }

                try {
                    essayMessageDTO = JsonUtils.jsonToPojo(message, UserMessageDTO.class);
                    if (essayMessageDTO == null) {
                        logger.info("接收essay-从redis中获取消息，转换为空, message:{}", message);
                        continue;
                    }
                } catch (Exception e) {
                    logger.info("接收essay-从redis中获取消息，转换异常, message:{}", message);
                    e.printStackTrace();
                }

                String type = essayMessageDTO.getType();

                UserMessageDTO finalEssayMessageDTO = essayMessageDTO;

                executorPool.execute(() -> {
                    try {
                        //atomicInteger.incrementAndGet();
                        semaphore.acquire();
                        MessageHandler messageManage = messageHandlers.stream()
                                .filter(item -> item.gerHandlerType().equals(type))
                                .findFirst().get();
                        messageManage.handler(finalEssayMessageDTO);
                        //atomicInteger.decrementAndGet();
                    } catch (InterruptedException e){
                        logger.info("消息处理异常，message:{}", message);
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                });
            }
        }
    }
}
