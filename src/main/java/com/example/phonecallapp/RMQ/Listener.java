package com.example.phonecallapp.RMQ;



import com.example.phonecallapp.mappers.MyMapper;
import com.example.phonecallapp.repository.CallDTO;
import com.example.phonecallapp.repository.CallRepo;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@EnableRabbit
public class Listener {
    @Autowired
    private CallRepo data;
    @Autowired
    private MyMapper myMapper;

    @RabbitListener(queues = "AppQueue")
    public void listener(@Payload CallDTO callDTO) throws IOException {
        data.save(myMapper.toEntity(callDTO));


    }
}
