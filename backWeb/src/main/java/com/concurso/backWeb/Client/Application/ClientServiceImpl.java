package com.concurso.backWeb.Client.Application;

import com.concurso.backWeb.Client.Domain.Client;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientInputDto;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backWeb.Client.Infrastructure.Repository.ClientRepository;
import com.concurso.backWeb.Others.Exceptions.myUnprocesableException;
import com.concurso.backWeb.Others.Kafka.Producer.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    KafkaSender sender;

    @Value("${server.port}")
    String port;

    @Value("${topic}")
    String topic;

    @Override
    public List<ClientOutputDto> getAllClients(){
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(ClientOutputDto::new).collect(Collectors.toList());
    }

    @Override
    public ClientOutputDto filterClientById(String id){
        Client client = clientRepository.findById(id).orElseThrow();
        return new ClientOutputDto(client);
    }

    @Override
    public ClientOutputDto filterClientByEmail(String email){
        Client client = clientRepository.findByEmail(email);
        return new ClientOutputDto(client);
    }

    @Override
    public ClientOutputDto addClient(ClientInputDto clientInputDto){
        if(clientRepository.findByEmail(clientInputDto.getEmail())==null){
            Client client = clientInputToEntity(clientInputDto);
            clientRepository.save(client);

            ClientOutputDto clientOutputDto = new ClientOutputDto(client);
            sender.sendMessage(topic,clientOutputDto,port,"create", "client" );
            return clientOutputDto;
        }

        throw new myUnprocesableException("Persona con email "+ clientInputDto.getEmail() + "ya existe");

    }

    @Override
    public ClientOutputDto updateClient(String id, ClientInputDto clientInputDto){
        if(clientRepository.findByEmail(clientInputDto.getEmail())==null){
            Client client = clientRepository.findById(id).orElseThrow();
            client.setName(clientInputDto.getName());
            client.setSurname(clientInputDto.getSurname());
            client.setPassword(clientInputDto.getPassword());
            client.setEmail(clientInputDto.getEmail());
            client.setPhone(clientInputDto.getPhone());

            clientRepository.save(client);
            ClientOutputDto clientOutputDto = new ClientOutputDto(client);
            sender.sendMessage(topic,clientOutputDto,port,"update", "client" );
            return clientOutputDto;
        }

        throw new myUnprocesableException("Persona con email "+ clientInputDto.getEmail() + "ya existe");

    }

    @Override
    public Client clientInputToEntity(ClientInputDto clientInputDto){
        Client client = new Client();
        client.setEmail(clientInputDto.getEmail());
        client.setName(clientInputDto.getName());
        client.setSurname(clientInputDto.getSurname());
        client.setPassword(clientInputDto.getPassword());
        client.setPhone(clientInputDto.getPhone());
        return client;
    }

    @Override
    public Client clientOutDtoToEntity(ClientOutputDto clientOutputDto){
        Client client = new Client();
        client.setEmail(clientOutputDto.getEmail());
        client.setName(clientOutputDto.getName());
        client.setSurname(clientOutputDto.getSurname());
        client.setPassword(clientOutputDto.getPassword());
        client.setPhone(clientOutputDto.getPhone());
        return client;
    }


    @Override
    public ClientOutputDto entityToClientOutDto(Client client){
        ClientOutputDto clientOutputDto = new ClientOutputDto(client);
        return clientOutputDto;
    }


}
