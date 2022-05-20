package com.concurso.backWeb.Client.Infrastructure.Kafka;

import com.concurso.backWeb.Client.Application.ClientService;
import com.concurso.backWeb.Client.Application.ClientServiceImpl;
import com.concurso.backWeb.Client.Domain.Client;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backWeb.Client.Infrastructure.Repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class KafkaClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientServiceImpl clientService;

    public void listenTopic(String action, ClientOutputDto clientOutputDto){
        switch (action){
            case "create" -> {
                Client client = clientService.clientOutDtoToEntity(clientOutputDto);
                System.out.println(client);
                clientRepository.save(client);
                System.out.println("CREATE SUCCESS");
            }

            case "update" -> {
                Client client = clientRepository.findById(clientOutputDto.getIdClient()).orElseThrow();
                client.setName(clientOutputDto.getName());
                client.setSurname(clientOutputDto.getSurname());
                client.setPassword(clientOutputDto.getPassword());

                clientRepository.save(client);
                System.out.println("UPDATE SUCCESS");
            }

            case "delete" -> {
                clientRepository.delete(clientRepository.findById(clientOutputDto.getIdClient()).orElseThrow());
                System.out.println("DELETE SUCCESS");
            }

            
        }
    }
}
