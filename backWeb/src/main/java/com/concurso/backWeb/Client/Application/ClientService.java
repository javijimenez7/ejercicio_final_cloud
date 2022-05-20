package com.concurso.backWeb.Client.Application;

import com.concurso.backWeb.Client.Domain.Client;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientInputDto;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientOutputDto;

import java.util.List;


public interface ClientService {
    List<ClientOutputDto> getAllClients();

    ClientOutputDto filterClientById(String id);

    ClientOutputDto filterClientByEmail(String email);

    ClientOutputDto addClient(ClientInputDto clientInputDto);

    ClientOutputDto updateClient(String id, ClientInputDto clientInputDto);

    Client clientInputToEntity(ClientInputDto clientInputDto);

    Client clientOutDtoToEntity(ClientOutputDto clientOutputDto);

    ClientOutputDto entityToClientOutDto(Client client);
}
