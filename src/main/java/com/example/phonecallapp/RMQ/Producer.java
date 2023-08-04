package com.example.phonecallapp.RMQ;

import com.example.phonecallapp.repository.CallDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service

public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;
    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;
    public void sendJsonMassage(CallDTO callDTO){
        rabbitTemplate.convertAndSend(exchange,routingKey, callDTO);

    }

}
