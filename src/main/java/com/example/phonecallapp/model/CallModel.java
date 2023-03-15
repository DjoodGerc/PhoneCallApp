package com.example.phonecallapp.model;

public class CallModel {
    private String callerPhoneNumber; //
    private String calledPhoneNumber; //
    private String callDuration; //call duration as "hh:mm:ss" example: 00:01:23
    private String callDate; //call date as "mm/dd/yy" example: 12/23/03

    public CallModel(String callerPhoneNumber, String calledPhoneNumber, String callDuration, String callDate) {
        this.callerPhoneNumber = callerPhoneNumber;
        this.calledPhoneNumber = calledPhoneNumber;
        this.callDuration = callDuration;
        this.callDate = callDate;
    }

    public CallModel() {
    }

    public String getCallerPhoneNumber() {
        return callerPhoneNumber;
    }

    public void setCallerPhoneNumber(String callerPhoneNumber) {
        this.callerPhoneNumber = callerPhoneNumber;
    }

    public String getCalledPhoneNumber() {
        return calledPhoneNumber;
    }

    public void setCalledPhoneNumber(String calledPhoneNumber) {
        this.calledPhoneNumber = calledPhoneNumber;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    @Override
    public String toString() {
        return "CallModel{" +
                "callerPhoneNumber='" + callerPhoneNumber + '\'' +
                ", calledPhoneNumber='" + calledPhoneNumber + '\'' +
                ", callDuration='" + callDuration + '\'' +
                ", callDate='" + callDate + '\'' +
                '}';
    }


}