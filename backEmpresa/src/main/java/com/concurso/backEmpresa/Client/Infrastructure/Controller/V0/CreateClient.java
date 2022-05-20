package com.concurso.backEmpresa.Client.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Client.Application.ClientServiceImpl;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientInputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("v0-empresa/client")
@RestController
public class CreateClient {
    @Autowired
    ClientServiceImpl clientService;

    @PostMapping
    public ClientOutputDto addClient(@RequestBody ClientInputDto clientInputDto) {
        return clientService.addClient(clientInputDto);
    }
}