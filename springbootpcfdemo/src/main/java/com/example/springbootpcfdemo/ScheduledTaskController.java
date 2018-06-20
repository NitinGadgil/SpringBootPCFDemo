package com.example.springbootpcfdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseEntity scheduleOpenEnrollment(@RequestBody OpenEnrollementSchedulingRequest openEnrollementSchedulingRequest) {
        MultiValueMap multiValueMap = new HttpHeaders();
        multiValueMap.add("Access-Control-Allow-Origin","*");
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        scheduleTask(openEnrollementSchedulingRequest);
        return responseEntity;
    }

    //    @Scheduled(cron = openEnrollmentCronVal)
//    public void notifyOpenEnrollment() {
//        logger.info("Current Thread : {}", Thread.currentThread().getName());
//        logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
//        logger.info(emailFrom);
//        logger.info(emailTo);
//        logger.info(emailSubject);
//        logger.info(emailText);
//        sendSimpleMessage();
//
//
//    }

    public void scheduleTask(OpenEnrollementSchedulingRequest openEnrollementSchedulingRequest){
        logger.info("Scheduling a Task");

//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
//        ScheduledTask scheduledTask = new ScheduledTask (openEnrollementSchedulingRequest);
//        executor.schedule(scheduledTask, openEnrollementSchedulingRequest.getFrequencyInSeconds() , TimeUnit.SECONDS);
//        executor.shutdown();
//

        ScheduledTask scheduledTask = new ScheduledTask (openEnrollementSchedulingRequest);
        Timer timer = new Timer();
        timer.schedule(scheduledTask, new Date(openEnrollementSchedulingRequest.getDateTimeOfYear()));

    }

    public void sendSimpleMessage(OpenEnrollementSchedulingRequest request) {

        logger.info("Executing a Scheduled Task set every "+request.getFrequencyInSeconds()+ " Seconds");
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(request.getEmailTo());
        emailMessage.setSubject(request.getEmailSubject());
        emailMessage.setText(request.getEmailText());
        emailSender.send(emailMessage);

    }


    public class ScheduledTask extends TimerTask implements Runnable {
        OpenEnrollementSchedulingRequest request;

        public ScheduledTask(OpenEnrollementSchedulingRequest request) {
            this.request = request;
        }

        @Override
        public void run() {
            sendSimpleMessage(request);
        }
    }


}