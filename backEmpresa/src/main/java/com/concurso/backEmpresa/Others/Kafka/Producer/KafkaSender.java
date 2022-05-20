package com.concurso.backEmpresa.Others.Kafka.Producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void sendMessage(String topic, Object obj, String port, String action, String clase){
        ObjectMapper mapper = new ObjectMapper();

        String jsonObject = null;
        try {
            jsonObject = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ignored){
            log.info("EXCEPCION AL PROCESAR EL JSON");
        }

        ProducerRecord<String,Object> producerRecord = new ProducerRecord<>(topic,jsonObject);
        producerRecord.headers().add("port", port.getBytes());
        producerRecord.headers().add("action", action.getBytes());
        producerRecord.headers().add("clase", clase.getBytes());

        log.info("Mensaje enviado");

        kafkaTemplate.send(producerRecord);
    }
}
