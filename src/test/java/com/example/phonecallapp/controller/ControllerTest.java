package com.example.phonecallapp.controller;


import com.example.phonecallapp.mappers.MyMapper;
import com.example.phonecallapp.repository.*;
import com.example.phonecallapp.service.MyService;
//import com.example.phonecallapp.wc.WebClientConf;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//тесты на контроллер
//@RunWith(SpringRunner.class)
//@DataJpaTest
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
//mock web server "честные тесты"
class ControllerTest {
    public static MockWebServer mockWebServer;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Autowired
    private CallRepo data;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MyMapper myMapper;
    @Autowired
    private MyService myService;

    @BeforeAll
    public static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

    }
    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    public  void prepareDb() throws IOException {
        if(!data.findAll().isEmpty()){
            data.deleteAll();
        }
    }

    @AfterEach
    public void resetDb() {
        data.deleteAll();
    }

    @PostConstruct
    private void initClients(){
        var mockWebServerUri = URI.create("http://"+ mockWebServer.getHostName()+":"+mockWebServer.getPort());
        ReflectionTestUtils.setField(myService,"baseUri",mockWebServerUri);
    }

    @Test
    void createAndExpFraudAndRoumingToUS() throws Exception {


        BlockStatus response = new BlockStatus(OffsetDateTime.now(), "BLOCKED");

        CallDTO callDTO = new CallDTO(7_113_666_6666L, 4_915_222_2222L
                , 15L, OffsetDateTime.of(2021, 4, 28,
                3, 20, 30, 0,
                ZoneOffset.ofHours(+4)));


        mockWebServer.enqueue(
                new MockResponse()
                        .addHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new BlockStatus(OffsetDateTime.now(),"BLOCKED")))
        );




        var req=mockMvc.perform(post("/saveCall")
                .content(objectMapper.writeValueAsString(callDTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        CallDTO saved = objectMapper.readValue(req.getResponse().getContentAsString(), CallDTO.class);

        int id=saved.getId();
        assertEquals(4_915_222_2222L,saved.getCalledPhoneNumber());
        assertEquals(7_113_666_6666L,saved.getCallerPhoneNumber());
        assertEquals(15L,data.findById(id).get().getCallDuration());
//        assertEquals( OffsetDateTime.of(2021, 4, 28,
//                3, 20, 30,0,
//                ZoneOffset.ofHours(+4)),data.findById(id).get().getCallDate());
        assertEquals(false,data.findById(id).get().getIs_spam());
        assertEquals(true,data.findById(id).get().getIs_fraud());
        assertEquals(true,data.findById(id).get().getIs_roaming());
        assertEquals("US",data.findById(id).get().getRoaming_country());
//        assertEquals(response,"12");
//        assertEquals(,mockWebServer.takeRequest());


    }
    @Test
    void createAndExpSpamAndNotRouming() throws Exception  {
        Long tonumb= 7_915_222_2220L;
        for (Long i = 0L; i < 7L; i++) {

            CallDTO callDTO =new CallDTO(7_916_666_3333L,tonumb, 15L, OffsetDateTime.now());
            data.saveAndFlush(myMapper.toEntity(callDTO));
            tonumb+=i;
        }
        CallDTO callDTO =new CallDTO(7_916_666_3333L,7_915_222_6666L, 15L, OffsetDateTime.now());
        var req=mockMvc.perform(post("/saveCall")
                .content(objectMapper.writeValueAsString(callDTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        CallDTO saved = objectMapper.readValue(req.getResponse().getContentAsString(), CallDTO.class);
        int id=saved.getId();
        assertEquals(7_916_666_3333L,data.findById(id).get().getCallerPhoneNumber());
        assertEquals(7_915_222_6666L,data.findById(id).get().getCalledPhoneNumber());
        assertEquals(true,data.findById(id).get().getIs_spam());
        assertEquals(false,data.findById(id).get().getIs_fraud());
        assertEquals(false,data.findById(id).get().getIs_roaming());
        assertEquals(null,data.findById(id).get().getRoaming_country());


    }



    @Test
    void readById() throws Exception {
        CallDTO callDTO =new CallDTO(7_666_666_6666L,4_915_222_2222L, 15L,
                OffsetDateTime.of(2023,4,17,20,0,0,0,ZoneOffset.ofHours(4)));
        CallEntity saved=data.saveAndFlush(myMapper.toEntity(callDTO));
        var req=mockMvc.perform(get("/Calls/{id}",saved.getId())).andExpect(status().isOk()).andReturn();
        CallEntity recievedCalldto =objectMapper.readValue(req.getResponse().getContentAsString(), CallEntity.class);
        CallDTO redao=myMapper.toDAO(recievedCalldto);
        assertEquals(saved.getId(), redao.getId());
        assertEquals(7_666_666_6666L,redao.getCallerPhoneNumber());
        assertEquals(4_915_222_2222L,redao.getCalledPhoneNumber());

        assertEquals(15L,redao.getCallDuration());
        assertEquals( OffsetDateTime.of(2023,4,17,
                20,0,0,0,ZoneOffset.ofHours(+4)),redao.getCallDate().withOffsetSameInstant(ZoneOffset.UTC));
    }

    @Test
    void testReadAll() throws Exception {
        CallDTO callDAOfst=new CallDTO(7_666_666_6666L,4_915_222_2222L, 15L, OffsetDateTime.of(2023,4,17,
                15,15,0,0,ZoneOffset.ofHours(3)));
        CallDTO callDAOsnd=new CallDTO(7_666_666_4444L,4_915_222_7777L, 30L, OffsetDateTime.of(2023,4,17,
                20,30,0,0,ZoneOffset.ofHours(3)));
        CallEntity saved1=data.save(myMapper.toEntity(callDAOfst));
        CallEntity saved2 =data.save(myMapper.toEntity(callDAOsnd));
        List<CallEntity> callEntityList =data.findAll();
        var req=mockMvc.perform(get("/Calls")).andExpect(status().isOk()).andReturn();
        List<CallDTO> recievedList = objectMapper.readValue(req.getResponse().getContentAsString(), new TypeReference<List<CallDTO>>(){});
        assertEquals(saved1.getId(),recievedList.get(0).getId());
        assertEquals(saved2.getId(),recievedList.get(1).getId());
        assertEquals(7_666_666_6666L,recievedList.get(0).getCallerPhoneNumber());
        assertEquals(4_915_222_2222L,recievedList.get(0).getCalledPhoneNumber());
        assertEquals(7_666_666_4444L,recievedList.get(1).getCallerPhoneNumber());
        assertEquals(4_915_222_7777L,recievedList.get(1).getCalledPhoneNumber());

    }
////
    @Test
    void testReadByCallerNumber() throws Exception {
        CallEntity callDTOfst=new CallEntity(1L,1L,33L, OffsetDateTime.now());
        CallEntity callDTOsnd=new CallEntity(2L,2L,66L, OffsetDateTime.now());
        CallEntity callDTOthrd=new CallEntity(1L,3L, 33L, OffsetDateTime.now());
        CallEntity callDTOfrth=new CallEntity(3L,2L, 66L, OffsetDateTime.now());
        data.save(callDTOfst);
        data.save(callDTOsnd);
        data.save(callDTOthrd);
        data.save(callDTOfrth);
        List<CallEntity> callDTOList=data.findByCallerPhoneNumber(1L);
        var req=mockMvc.perform(get("/Calls/byNumber?ident=Caller&number=1")).andExpect(status().isOk()).andReturn();
        List<CallDTO> recievedList = objectMapper.readValue(req.getResponse().getContentAsString(), new TypeReference<List<CallDTO>>(){});

        assertEquals(1L,recievedList.get(0).getCallerPhoneNumber());
        assertEquals(1L,recievedList.get(0).getCalledPhoneNumber());
        assertEquals(1L,recievedList.get(1).getCallerPhoneNumber());
        assertEquals(3L,recievedList.get(1).getCalledPhoneNumber());

    }
    @Test
    void testReadByCalledNumber() throws Exception {
        CallEntity callDTOfst=new CallEntity(1L,1L,33L, OffsetDateTime.now());
        CallEntity callDTOsnd=new CallEntity(2L,2L,66L, OffsetDateTime.now());
        CallEntity callDTOthrd=new CallEntity(1L,3L, 33L, OffsetDateTime.now());
        CallEntity callDTOfrth=new CallEntity(3L,2L, 66L, OffsetDateTime.now());
        data.save(callDTOfst);
        data.save(callDTOsnd);
        data.save(callDTOthrd);
        data.save(callDTOfrth);
        List<CallEntity> callDTOList=data.findByCalledPhoneNumber(2L);
        var req=mockMvc.perform(get("/Calls/byNumber?ident=Called&number=2")).andExpect(status().isOk()).andReturn();
        List<CallDTO> recievedList = objectMapper.readValue(req.getResponse().getContentAsString(), new TypeReference<List<CallDTO>>(){});

        assertEquals(2L,recievedList.get(0).getCallerPhoneNumber());
        assertEquals(2L,recievedList.get(0).getCalledPhoneNumber());
        assertEquals(3L,recievedList.get(1).getCallerPhoneNumber());
        assertEquals(2L,recievedList.get(1).getCalledPhoneNumber());
    }
}