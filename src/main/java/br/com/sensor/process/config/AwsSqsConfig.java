package br.com.sensor.process.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class AwsSqsConfig {

    //@Value("${app.aws.sqs.mock.endpoint}")
    //private String awsSqsMockEndpoint;

    @Bean
    @Primary
    public AmazonSQSAsync awsSqsClientMock(){
        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAVTNSV2FARM75QDXE", "QJpKJX+JjJpP016C+X2DzyO5JfbQfRs2k2d43yWI")))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://sqs.us-east-1.amazonaws.com/385311166785/myQueue", "us-east-1"))
                .build();
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(AmazonSQSAsync amazonSQSAsync, QueueMessageHandler queueMessageHandler) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setAmazonSqs(amazonSQSAsync);
        simpleMessageListenerContainer.setMessageHandler(queueMessageHandler);
        simpleMessageListenerContainer.setMaxNumberOfMessages(10);
        simpleMessageListenerContainer.setTaskExecutor(threadPoolTaskExecutor());
        return simpleMessageListenerContainer;
    }

    @Bean
    public QueueMessageHandler queueMessageHandler(AmazonSQSAsync amazonSQSAsync) {
        QueueMessageHandlerFactory queueMessageHandlerFactory = new QueueMessageHandlerFactory();
        queueMessageHandlerFactory.setAmazonSqs(amazonSQSAsync);
        QueueMessageHandler queueMessageHandler = queueMessageHandlerFactory.createQueueMessageHandler();
        return queueMessageHandler;
    }


    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.initialize();
        return executor;
    }

}