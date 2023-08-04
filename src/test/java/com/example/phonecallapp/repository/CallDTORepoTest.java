/*
package com.example.phonecallapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;


//@RunWith(SpringRunner.class)

@SpringBootTest
class CallDTORepoTest {


    @Autowired
    private CallRepo data;





    @Test
    public void getFieldTest(){
        CallDTO callDTO;

        callDTO = data.findById(1).get();
        assertEquals(callDTO,new CallDTO("111","1111",1,new Time(00,00,11),new Date(2023,11,11 )));

    }

}*/
