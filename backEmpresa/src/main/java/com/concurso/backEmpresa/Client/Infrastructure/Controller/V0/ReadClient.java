package com.concurso.backEmpresa.Client.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Client.Application.ClientServiceImpl;
import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("v0-empresa/client")
@RestController
public class ReadClient {

    @Autowired
    ClientServiceImpl clientService;

    @GetMapping
    public List<ClientOutputDto> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("{email}")
    public ClientOutputDto getClientByEmail(@PathVariable String email){
        return clientService.filterClientByEmail(email);
    }
}
