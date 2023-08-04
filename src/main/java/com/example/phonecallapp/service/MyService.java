package com.example.phonecallapp.service;

import com.example.phonecallapp.RMQ.Producer;
import com.example.phonecallapp.configuration.CountryCodes;
import com.example.phonecallapp.mappers.MyMapper;
import com.example.phonecallapp.repository.*;
//import com.example.phonecallapp.wc.WebClientConf;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Slf4j
public class MyService {

    private URI baseUri=URI.create("http://localhost:8080");
    @Autowired
    private CallRepo data;
//    @Autowired
    private WebClient client;
    @Autowired
    private Producer producer;
    @Autowired
    private MyMapper myMapper;
    @Autowired
    private List<CountryCodes> countriesCodes;
    @Autowired
    private ObjectMapper objectMapper;
//    private CallDAO callDAO;

    public CallDTO fraudAndRoamingValidation(CallDTO callDTO){
        String frpstr = callDTO.getCallerPhoneNumber().toString();
        String topstr = callDTO.getCalledPhoneNumber().toString();
        String FROMcc= frpstr.substring(0, frpstr.length()-10);
        //98 916 554 8548
        String FROMnumber= frpstr.substring(frpstr.length()-10, frpstr.length());
        String FROMoperator = frpstr.substring(frpstr.length()-10, frpstr.length()-7);
        String TOcc=topstr.substring(0, frpstr.length()-10);

        {
            for (int i = 0; i < countriesCodes.size(); i++) {
                CountryCodes ctry= countriesCodes.get(i);
                if (ctry.getCode().equals(FROMcc)  & ctry.getFraudNumbers().contains(FROMnumber)
                        | FROMcc.equals("7") & ctry.getCode().equals("7") & !ctry.getOperators().contains(FROMoperator)) {
                    callDTO.setIs_fraud(true);
                }//на fraud
                else if (!TOcc.equals("7")& ctry.getCode().equals(TOcc)){
                    callDTO.setIs_roaming(true);
                    callDTO.setRoaming_country(ctry.getName());
                }//на rouming

            }
            if (callDTO.getIs_fraud()==null){
                callDTO.setIs_fraud(false);};
            if(callDTO.getIs_roaming()==null){
                callDTO.setIs_roaming(false);};

        }
        List<CallDTO> calls =myMapper.toDAOList(data.findByCallerPhoneNumberAndCallDateAfter(callDTO.getCallerPhoneNumber(), OffsetDateTime.now().minusMinutes(10)));
        List<Long> numbers= new ArrayList<>();
        int k=0;
        for (CallDTO dao : calls) {
            if (!numbers.contains(dao.getCalledPhoneNumber())) {
                k += 1;
                numbers.add(dao.getCalledPhoneNumber());
            }
        }
        callDTO.setIs_spam(k >= 5);
        if (callDTO.getIs_fraud()){
            producer.sendJsonMassage(callDTO);

           client=WebClient.create(String.valueOf(baseUri));
            var req=client.post().uri("/block").
                    bodyValue(new Block(callDTO.getCallerPhoneNumber(),"Some Reason idk")).
                    retrieve()/*.onStatus(HttpStatusCode::isError, ClientResponse::createException)*/.bodyToMono(BlockStatus.class).block();
            try {
                log.info(objectMapper.writeValueAsString(req));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
        CallEntity centity=data.saveAndFlush(myMapper.toEntity(callDTO));

        callDTO.setId(centity.getId());

        return callDTO;
    }


}
