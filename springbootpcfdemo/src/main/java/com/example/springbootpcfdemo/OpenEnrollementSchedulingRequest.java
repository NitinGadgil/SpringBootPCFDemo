package com.example.springbootpcfdemo;


import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OpenEnrollementSchedulingRequest {

    private String emailTo;

    private String emailText;

    private String emailSubject;

    private int frequencyInSeconds;

    private String dateTimeOfYear;



    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public int getFrequencyInSeconds() {
        return frequencyInSeconds;
    }

    public void setFrequencyInSeconds(int frequencyInSeconds) {
        this.frequencyInSeconds = frequencyInSeconds;
    }

    public String getDateTimeOfYear() {
        return dateTimeOfYear;
    }

    public void setDateTimeOfYear(String dateTimeOfYear) {
        this.dateTimeOfYear = dateTimeOfYear;
    }
}
