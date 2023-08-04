package com.example.phonecallapp.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@builder посмори
public class CallDTO {
    private Integer id;

    private Long callerPhoneNumber; //->long

    private Long calledPhoneNumber; //->long

//localdatetime and offsetdatetime
    private Long callDuration;

    private OffsetDateTime callDate;

    private Boolean is_spam;

    private Boolean is_roaming;

    private String roaming_country;

    private  Boolean is_fraud;

    public CallDTO(Long callerPhoneNumber, Long calledPhoneNumber, Long callDuration, OffsetDateTime callDate) {
        this.callerPhoneNumber = callerPhoneNumber;
        this.calledPhoneNumber = calledPhoneNumber;
        this.callDuration = callDuration;
        this.callDate = callDate;
    }



}