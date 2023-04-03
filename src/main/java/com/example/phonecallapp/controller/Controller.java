package com.example.phonecallapp.controller;


import com.example.phonecallapp.mappers.DAOMapper;
import com.example.phonecallapp.mappers.DTOListMapper;
import com.example.phonecallapp.mappers.DTOMapper;
import com.example.phonecallapp.repository.CallDAO;
import com.example.phonecallapp.repository.CallDTO;
import com.example.phonecallapp.repository.CallRepo;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private CallRepo data;
    @Autowired
    private DAOMapper daoMapper;
    @Autowired
    private DTOListMapper dtoListMapper;
    @Autowired
    private DTOMapper dtoMapper;


//    private CallService callService;

    @PostMapping(value = "/saveCall")
    public ResponseEntity<?> create(@RequestBody CallDAO callDAO){

        data.save(daoMapper.toDTO(callDAO));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/Calls")
    public ResponseEntity<List<CallDAO>> read() {
        final List<CallDAO> calls =dtoListMapper.toDAOList(data.findAll());
        if (calls.isEmpty() ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(calls, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Calls/{id}")
    public ResponseEntity<CallDAO> read(@PathVariable(name = "id") Integer id) {
        CallDAO callDAO = dtoMapper.toDAO(data.findById(id).get());
        return new ResponseEntity<>(callDAO, HttpStatus.OK);

    }
    //@requestparams caller/called number
    @GetMapping(value = "/Calls/byNumber")
    public ResponseEntity<List<CallDAO>> read(@RequestParam String ident,@RequestParam String number) {
        if (ident.equals("Caller")) {
            final List<CallDAO> calls = dtoListMapper.toDAOList(data.findByCallerPhoneNumber(number));
            return new ResponseEntity<>(calls, HttpStatus.OK);
        }
        else if (ident.equals("Called")){
            final List<CallDAO> calls = dtoListMapper.toDAOList(data.findByCalledPhoneNumber(number));
            return new ResponseEntity<>(calls, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
