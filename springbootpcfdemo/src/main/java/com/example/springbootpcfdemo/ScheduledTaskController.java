package com.example.springbootpcfdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "/api/open-enrollment", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduledTaskController {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskController.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Value("${notification.emailFrom}")
    private String emailFrom;

    //@Value("${notification.emailTo}")
    private String emailTo;

    //@Value("${notification.emailSubject}")
    private String emailSubject;

    //@Value("${notification.emailText}")
    private String emailText;

    private static final String openEnrollmentCronVal = "0 * * * * ?";

    @Autowired
    public JavaMailSender emailSender;

    @Scheduled(cron = openEnrollmentCronVal)
    public void notifyOpenEnrollment() {
        logger.info("Current Thread : {}", Thread.currentThread().getName());
        logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        logger.info(emailFrom);
        logger.info(emailTo);
        logger.info(emailSubject);
        logger.info(emailText);
        sendSimpleMessage();


    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseEntity scheduleOpenEnrollment(@RequestBody OpenEnrollementSchedulingRequest openEnrollementSchedulingRequest) {
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);

        logger.info("Setting Request Values");
        this.emailTo = openEnrollementSchedulingRequest.getEmailTo();
        this.emailSubject = openEnrollementSchedulingRequest.getEmailSubject();
        this.emailText = openEnrollementSchedulingRequest.getEmailText();

        return responseEntity;
    }

    public void sendSimpleMessage( ) {

        if(emailTo != null && emailText != null && emailSubject != null){
            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setTo(emailTo);
            emailMessage.setSubject(emailSubject);
            emailMessage.setText(emailText);
            emailSender.send(emailMessage);
            clearValues();
        }

    }

    public void clearValues(){
        this.emailTo = null;
        this.emailSubject = null;
        this.emailText = null;
    }
}