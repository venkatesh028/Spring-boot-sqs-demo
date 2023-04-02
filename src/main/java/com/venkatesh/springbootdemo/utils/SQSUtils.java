package com.venkatesh.springbootdemo.utils;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.venkatesh.springbootdemo.config.SQSConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SQSUtils {

    public static boolean uploadMessageToQueue(AmazonSQS amazonSQS, String name) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("AttributeOne", new MessageAttributeValue()
                .withStringValue("This is an attribute")
                .withDataType("String"));
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(String.valueOf(amazonSQS.getQueueUrl(SQSConfig.queueName).getQueueUrl()))
                .withMessageBody(name)
                .withMessageGroupId("baeldung-group-1")
                .withMessageAttributes(messageAttributes);
        amazonSQS.sendMessage(sendMessageRequest);
        return true;
    }
}
