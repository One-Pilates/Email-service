package com.onepilates.email_service.infrastructure.config;

import org.springframework.amqp.core.Queue;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbitmq.email-queue:email.queue}")
    private String queueName;

    @Bean
    public Queue emailQueue() {
        return new Queue(queueName, true);
    }


}