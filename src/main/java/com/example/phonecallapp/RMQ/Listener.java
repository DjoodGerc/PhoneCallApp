package com.example.phonecallapp.RMQ;

import com.example.phonecallapp.dataacces.DataAccessObject;
import com.example.phonecallapp.model.CallModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@Component
@EnableRabbit
public class Listener {

    private ConnectionFactory factory =new ConnectionFactory();
        private Connection conn;

    {
        try {
            conn = factory.newConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
    @RabbitListener(queues = "AppQueue")
    public void listener(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        CallModel call;
        try {
            call = mapper.readValue(jsonString, CallModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        DataAccessObject.setField(call);
    }
}
