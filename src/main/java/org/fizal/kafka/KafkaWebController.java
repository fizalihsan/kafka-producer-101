package org.fizal.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka/")
public class KafkaWebController {
    @Value("${message.topic.name}")
    private String topicName;

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping(value="/producer")
    public String producer(String message){
        for (int i = 0; i < 100; i++) {
            kafkaProducer.send(message +  "------" + i);
        }

        return "Message sent to the Kafka Topic '" + topicName + "' successfully";
    }
}
