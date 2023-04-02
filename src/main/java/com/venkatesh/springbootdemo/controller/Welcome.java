package com.venkatesh.springbootdemo.controller;

import com.amazonaws.services.sqs.AmazonSQS;
import com.venkatesh.springbootdemo.utils.SQSUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Welcome {

    private final AmazonSQS amazonSQS;

    public Welcome(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    @GetMapping("/hello/{user}")
    public String getHello(@PathVariable(name = "user") String user){
     return "Hello "+user;
    }

    @GetMapping("/queue/{secret}")
    public String sendToQueue(@PathVariable(name = "secret") String secret) {
        SQSUtils.uploadMessageToQueue(amazonSQS, secret);
        return "Success";
    }
}
