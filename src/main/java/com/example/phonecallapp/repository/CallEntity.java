package com.example.phonecallapp.repository;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;


@Table(name = "calls_info")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CallEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="caller_phone_number")
    private Long callerPhoneNumber; //
    @Column(name="called_phone_number")
    private Long calledPhoneNumber; //

    //localdatetime and offsetdatetime
    @Column(name="call_duration")
    private Long callDuration;
    @Column(name = "call_date",columnDefinition = "timestamp with time zone")
    private OffsetDateTime callDate;

    @Column(name = "is_spam")
    private Boolean is_spam;

    @Column(name = "is_roaming")
    private  Boolean is_roaming;

    @Column(name = "roaming_country")
    private String roaming_country;

    @Column(name = "is_fraud")
    private  Boolean is_fraud;


    public CallEntity(Long callerPhoneNumber, Long calledPhoneNumber, Long callDuration, OffsetDateTime callDate) {
        this.callerPhoneNumber = callerPhoneNumber;
        this.calledPhoneNumber = calledPhoneNumber;
        this.callDuration = callDuration;
        this.callDate = callDate;
    }
}
