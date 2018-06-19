package com.example.springbootpcfdemo;


public class OpenEnrollementSchedulingRequest {

    private String emailTo;

    private String emailText;

    private String emailSubject;



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
}
