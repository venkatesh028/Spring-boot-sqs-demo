package com.venkatesh.springbootdemo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SQSConfig {

    public static final String queueName = "batchIDs.fifo";

    public static final String TEST_ACCESS_KEY = "test";

    public static final String TEST_SECRET_KEY = "test";

    public static final Regions DEFAULT_US_WEST_2 = Regions.US_WEST_2;

    @Bean
    @Primary
    public AmazonSQSAsync buildAmazonSQS(){
        AmazonSQSAsync amazonSQSAsync = AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:14573", DEFAULT_US_WEST_2.name()))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(TEST_ACCESS_KEY, TEST_SECRET_KEY)))
                .build();
        createQueues(amazonSQSAsync, queueName);
        return amazonSQSAsync;
    }

    private void createQueues(final AmazonSQSAsync amazonSQSAsync, final  String queueName) {
        Map<String, String> queueAttributes = new HashMap<>();
        queueAttributes.put("FifoQueue", "true");
        queueAttributes.put("ContentBasedDeduplication", "true");
        CreateQueueRequest createFifoQueueRequest = new CreateQueueRequest(
                queueName).withAttributes(queueAttributes);

        amazonSQSAsync.createQueue(createFifoQueueRequest);
    }


}
