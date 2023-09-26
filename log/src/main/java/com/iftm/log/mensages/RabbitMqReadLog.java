package com.iftm.log.mensages;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.iftm.log.models.dtos.LogDTO;
import com.iftm.log.services.LogService;

@Component
public class RabbitMqReadLog {

@Autowired
private LogService logService;

@RabbitListener(queues = "${newsLetter.rabbitmq.queue}")
public void receiveLog(@Payload LogDTO logDTO){
    if(logDTO != null)
            logService.save(logDTO);
}


    
}
