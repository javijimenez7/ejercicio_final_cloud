package com.concurso.backEmpresa.Others.Kafka.Listener;

import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Kafka.KafkaClientService;
import com.concurso.backEmpresa.Mail.Domain.Mail;
import com.concurso.backEmpresa.Mail.Infrastructure.Kafka.KafkaMailService;
import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
import com.concurso.backEmpresa.Reserva.Infrastructure.Kafka.KafkaReservaService;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Kafka.KafkaTripService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaRouter {

    @Value("${server.port}")
    String puerto;

    @Autowired
    KafkaClientService kafkaClientService;
    @Autowired
    KafkaReservaService kafkareservaService;
    @Autowired
    KafkaTripService kafkaTripService;

    @Autowired
    KafkaMailService kafkaMailService;

    @KafkaListener(topics = "${topic}", groupId = "${group}")
    public void listenTopic(@Payload ConsumerRecord<Object,String> record) throws JsonProcessingException{

        final String[] action = new String[1];
        final String[] port = new String[1];
        final String[] clase = new String[1];
        ObjectMapper mapper = new ObjectMapper();

        record.headers().headers("port").forEach(header -> {
            port[0] = new String(header.value());
        });

        record.headers().headers("clase").forEach(header -> {
            clase[0] = new String(header.value());
        });

        record.headers().headers("action").forEach(header -> {
            action[0] = new String(header.value());
        });


        if(!port[0].equals(puerto)){
            log.info("MENSAJE RECIBIDO");

            switch (clase[0]){

                case "client" ->{
                    log.info("RECIBIDO CLIENTE: ACCION: "+action[0]);
                    kafkaClientService.listenTopic(action[0], mapper.readValue((String)record.value(), ClientOutputDto.class));
                }

                case "reserva" ->{
                    log.info("RECIBIDO RESERVA: ACCION: "+action[0]);
                    kafkareservaService.listenTopic(action[0], mapper.readValue((String) record.value(), ReservaOutputDto.class));
                }
                
                case "trip" -> {
                    log.info("RECIBIDO TRIP: ACCION: "+action[0]);
                    kafkaTripService.listenTopic(action[0], mapper.readValue((String) record.value(), TripOutputDto.class));
                }

                case "mail" -> {
                    log.info("RECIBIDO MAIL! accion: " + action[0]);
                    kafkaMailService.listenTopic(action[0], mapper.readValue((String)record.value(), Mail.class));
                }
                
            }
        }
    }

}
