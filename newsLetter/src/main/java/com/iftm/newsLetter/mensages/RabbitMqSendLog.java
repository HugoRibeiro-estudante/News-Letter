package com.iftm.newsLetter.mensages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.iftm.newsLetter.models.dtos.LogDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


@Component
public class RabbitMqSendLog {

    @Value("${newsLetter.rabbitmq.exchange}")
    private String exchange;

    @Value("${newsLetter.rabbitmq.rountingkey}")
    private String routingKey;

    @Value("${newsLetter.rabbitmq.queue}")
    private String queue;


    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSendLog.class);

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSendLog(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendLog(LogDTO logDTO) {

        logger.info("Valor de exchange: {}", exchange);
        logger.info("Valor de routingKey: {}", routingKey);
        logger.info("Valor de queue: {}", queue);

            rabbitTemplate.execute(channel -> {
                channel.exchangeDeclare(exchange, "direct", true);
                channel.queueDeclare(queue, true, false, false, null);
                channel.queueBind(queue, exchange, routingKey);
                return null;
            });
            rabbitTemplate.convertAndSend(exchange, routingKey, logDTO);

    }
}
