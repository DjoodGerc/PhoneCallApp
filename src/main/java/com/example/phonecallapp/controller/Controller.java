package com.example.phonecallapp.controller;



import com.example.phonecallapp.configuration.CountryCodes;
import com.example.phonecallapp.RMQ.Producer;
//import com.example.phonecallapp.mappers.DTOListMapper;

import com.example.phonecallapp.mappers.MyMapper;
import com.example.phonecallapp.repository.*;
import com.example.phonecallapp.service.MyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Data
@RestController
public class Controller {
    @Autowired
    private CallRepo data;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MyMapper myMapper;

    @Autowired
    private Producer producer;

    @Autowired
    private List<CountryCodes> countriesCodes;
    @Autowired
    private MyService myService;
//    @Autowired
//    private WebClient client;

//    private CallService callService;

    @PostMapping(value = "/saveCall")
    public ResponseEntity<CallDTO> create(@RequestBody CallDTO callDTO){
        return new ResponseEntity<>(myService.fraudAndRoamingValidation(callDTO),HttpStatus.CREATED);
    }

    @GetMapping(value = "/Calls")
    public ResponseEntity<List<CallDTO>> read() {
        final List<CallDTO> calls =myMapper.toDAOList(data.findAll());
        if (calls.isEmpty() ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(calls, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Calls/{id}")
    public ResponseEntity<CallDTO> read(@PathVariable(name = "id") Integer id) {
        CallDTO callDTO = myMapper.toDAO(data.findById(id).get());
        return new ResponseEntity<>(callDTO, HttpStatus.OK);

    }
    //@requestparams caller/called number
    @GetMapping(value = "/Calls/byNumber")
    public ResponseEntity<List<CallDTO>> read(@RequestParam String ident, @RequestParam Long number) {
        if (ident.equals("Caller")) {
            final List<CallDTO> calls = myMapper.toDAOList(data.findByCallerPhoneNumber(number));
            return new ResponseEntity<>(calls, HttpStatus.OK);
        }
        else if (ident.equals("Called")){
            final List<CallDTO> calls = myMapper.toDAOList(data.findByCalledPhoneNumber(number));
            return new ResponseEntity<>(calls, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
