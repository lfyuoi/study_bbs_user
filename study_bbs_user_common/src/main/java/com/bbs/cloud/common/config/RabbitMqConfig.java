package com.bbs.cloud.common.config;

import  com.bbs.cloud.common.contant.RabbitContant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue userQueue(){
        return new Queue(RabbitContant.USER_QUEUE_NAME);
    }

    @Bean
    public DirectExchange userExchange(){
        return new DirectExchange(RabbitContant.USER_EXCHANGE_NAME);
    }

    @Bean
    public Binding userBind(Queue userQueue,DirectExchange userExchange){
        return BindingBuilder.bind(userQueue).to(userExchange).with(RabbitContant.USER_ROUTING_KEY);
    }

    @Bean
    public Queue essayQueue(){
        return new Queue(RabbitContant.ESSAY_QUEUE_NAME);
    }

    @Bean
    public DirectExchange essayExchange(){
        return new DirectExchange(RabbitContant.ESSAY_EXCHANGE_NAME);
    }

    @Bean
    public Binding essayBind(Queue essayQueue,DirectExchange essayExchange){
        return BindingBuilder.bind(essayQueue).to(essayExchange).with(RabbitContant.ESSAY_ROUTING_KEY);
    }
}
