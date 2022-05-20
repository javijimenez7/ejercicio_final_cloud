package com.concurso.backWeb.Client.Infrastructure.Controller.V0;

import com.concurso.backWeb.Client.Application.ClientServiceImpl;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientInputDto;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v0/client")

public class CreateClient {

    @Autowired
    ClientServiceImpl clientService;

    @PostMapping
    public ClientOutputDto addClient(@RequestBody ClientInputDto clientInputDto){
        return clientService.addClient(clientInputDto);
    }

}
