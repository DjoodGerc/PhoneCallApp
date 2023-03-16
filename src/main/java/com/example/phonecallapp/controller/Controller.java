package com.example.phonecallapp.controller;

import com.example.phonecallapp.dataacces.DataAccessObject;
import com.example.phonecallapp.model.CallModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private DataAccessObject data;

    @PostMapping(value = "/saveCall")
    public ResponseEntity<?> create(@RequestBody CallModel call) {
        DataAccessObject.setField(call);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping(value = "/Calls")
    public ResponseEntity<List<CallModel>> read() {
        final List<CallModel> calls = DataAccessObject.getAllFields();
        if (calls.isEmpty() && calls != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(calls, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Calls/{id}")
    public ResponseEntity<CallModel> read(@PathVariable(name = "id") int id) {
        final CallModel call = DataAccessObject.getFieldById(id);
        if (call != null) {
            return new ResponseEntity<>(call, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
