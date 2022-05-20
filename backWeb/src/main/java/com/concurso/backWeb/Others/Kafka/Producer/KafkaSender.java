package com.concurso.backWeb.Others.Kafka.Producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
@Slf4j

public class KafkaSender {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Object obj, String port, String action, String clase){
        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = null;

        try {
            jsonObject = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ignored){}

        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, jsonObject);
        producerRecord.headers().add("port", port.getBytes());
        producerRecord.headers().add("clase", clase.getBytes());
        producerRecord.headers().add("action", action.getBytes());

        System.out.println("Mensaje enviado con exito");
        System.out.println(producerRecord);
        kafkaTemplate.send(producerRecord);
    }
}
