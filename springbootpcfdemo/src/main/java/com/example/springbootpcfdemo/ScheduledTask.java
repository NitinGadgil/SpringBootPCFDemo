package com.example.springbootpcfdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ScheduledTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    OpenEnrollementSchedulingRequest request;

    @Autowired
    ScheduledTaskController controller;

    public ScheduledTask(OpenEnrollementSchedulingRequest request) {
         this.request = request;
    }

    @Override
    public void run() {
        controller.sendSimpleMessage(request);
    }


}
