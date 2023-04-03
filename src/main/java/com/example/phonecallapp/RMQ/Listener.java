package com.example.phonecallapp.RMQ;


import com.example.phonecallapp.mappers.DAOMapper;
import com.example.phonecallapp.repository.CallDAO;
import com.example.phonecallapp.repository.CallDTO;
import com.example.phonecallapp.repository.CallRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


@Component
@EnableRabbit
public class Listener {
    @Autowired
    private CallRepo data;
    @Autowired
    private DAOMapper daoMapper;
    @RabbitListener(queues = "AppQueue")
    public void listener(@Payload CallDAO callDAO) throws IOException {
        data.save(daoMapper.toDTO(callDAO));


    }
}
