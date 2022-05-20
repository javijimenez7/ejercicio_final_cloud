package com.concurso.backEmpresa.Client.Application;

import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientInputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Repository.ClientRepository;
import com.concurso.backEmpresa.Others.Exceptions.customUnprocesableException;
import com.concurso.backEmpresa.Others.Kafka.Producer.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
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
        return new ClientOutputDto(clientRepository.findById(id).orElseThrow());
    }

    @Override
    public ClientOutputDto filterClientByEmail(String email){
        return new ClientOutputDto(clientRepository.findByEmail(email));
    }

    @Override
    public ClientOutputDto addClient(ClientInputDto clientInputDto){
        if(clientRepository.findByEmail(clientInputDto.getEmail()) == null){
            Client client = clientInputToEntity(clientInputDto);
            clientRepository.save(client);

            ClientOutputDto clientOutputDto = new ClientOutputDto(client);
            sender.sendMessage(topic, clientOutputDto, port, "create", "client");
            return clientOutputDto;
        }
        throw new customUnprocesableException("Persona con email " + clientInputDto.getEmail() + " ya existe");
    }

    @Override
    public ClientOutputDto updateClient(String id, ClientInputDto clientInputDto){

        Client client = clientRepository.findById(id).orElseThrow();
        client.setEmail(clientInputDto.getEmail());
        client.setPassword(clientInputDto.getPassword());
        client.setPhone(clientInputDto.getPhone());
        client.setSurname(clientInputDto.getSurname());
        client.setName(clientInputDto.getName());

        clientRepository.save(client);
        ClientOutputDto clientOutputDto = new ClientOutputDto(client);
        sender.sendMessage(topic, clientOutputDto, port, "update", "client");
        return clientOutputDto;

    }

    @Override
    public void deleteClient(String id){
        ClientOutputDto clientOutputDto = entityToClientOutDto(clientRepository.findById(id).orElseThrow());
        clientRepository.delete(clientRepository.findById(id).orElseThrow());
        sender.sendMessage(topic, clientOutputDto, port, "delete", "client");
    }


    public Client clientInputToEntity(ClientInputDto clientInputDto){
        Client client = new Client();
        client.setEmail(clientInputDto.getEmail());
        client.setName(clientInputDto.getName());
        client.setSurname(clientInputDto.getSurname());
        client.setPassword(clientInputDto.getPassword());
        client.setPhone(clientInputDto.getPhone());
        return client;
    }


    public Client clientOutDtoToEntity(ClientOutputDto clientOutputDto){
        Client client = new Client();
        client.setIdClient(clientOutputDto.getIdClient());
        client.setEmail(clientOutputDto.getEmail());
        client.setName(clientOutputDto.getName());
        client.setSurname(clientOutputDto.getSurname());
        client.setPassword(clientOutputDto.getPassword());
        client.setPhone(clientOutputDto.getPhone());
        return client;
    }



    public ClientOutputDto entityToClientOutDto(Client client){
        return new ClientOutputDto(client);
    }

}
