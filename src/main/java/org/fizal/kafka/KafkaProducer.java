package org.fizal.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TimeZone;

@Service
public class KafkaProducer {

    @Value("${message.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message){
        System.out.println("Sending a message = " + message);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        Map<MetricName, ? extends Metric> metrics = kafkaTemplate.metrics();
        System.out.println(metrics);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable e) {
                System.out.println("Unable to send message = [" + message + "] due to : " + e.getMessage());
            }



            @Override
            public void onSuccess(SendResult<String, String> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                System.out.println("Send message=[" + message + "] to topic =" + metadata.topic() +
                        " (partition = " + metadata.partition() + ") with offset=[" + metadata.offset() + "] at " + toDateTime(metadata.timestamp())
                );
            }
        });
    }

    private LocalDateTime toDateTime(long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }
}
