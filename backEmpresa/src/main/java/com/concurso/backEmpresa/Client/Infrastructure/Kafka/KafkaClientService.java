package com.concurso.backEmpresa.Client.Infrastructure.Kafka;

import com.concurso.backEmpresa.Client.Application.ClientServiceImpl;
import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaClientService {


    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientServiceImpl clientService;

    public void listenTopic(String action, ClientOutputDto clientOutputDto){

        switch (action){

            case "create" -> {
                Client client = clientService.clientOutDtoToEntity(clientOutputDto);
                clientRepository.save(client);
                log.info("CREATE SUCCESS");
            }

            case "update" -> {
                Client client = clientRepository.findById(clientOutputDto.getIdClient()).orElseThrow();
                client.setName(clientOutputDto.getName());
                client.setPhone(clientOutputDto.getPhone());
                client.setPassword(clientOutputDto.getPassword());
                client.setSurname(clientOutputDto.getSurname());
                log.info("UPDATE SUCCESS");
            }

            case "delete" -> {
                clientRepository.delete(clientRepository.findById(clientOutputDto.getIdClient()).orElseThrow());
                log.info("DELETE SUCCESS");
            }
        }
    }
}
