package com.concurso.backEmpresa.Client.Application;

import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientInputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;

import java.util.List;


public interface ClientService {
    List<ClientOutputDto> getAllClients();

    ClientOutputDto filterClientById(String id);

    ClientOutputDto filterClientByEmail(String email);

    ClientOutputDto addClient(ClientInputDto clientInputDto);

    ClientOutputDto updateClient(String id, ClientInputDto clientInputDto);

    void deleteClient(String id);


}
