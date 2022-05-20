package com.concurso.backWeb.Client.Infrastructure.Controller.V0;

import com.concurso.backWeb.Client.Application.ClientServiceImpl;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientInputDto;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backWeb.Others.Auth.AuthUtils;
import com.concurso.backWeb.Others.Exceptions.myUnprocesableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("v0/client")

public class UpdateClient {

    @Autowired
    ClientServiceImpl clientService;

    @Value("${urlempresa}")
    String EMPRESA;

    @PutMapping("{id}")
    public ClientOutputDto updateClient(@PathVariable String id, @RequestBody ClientInputDto clientInputDto, @RequestHeader("Authorization") String auth){
        HttpEntity<Object> request = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Void> re = new RestTemplate().exchange(EMPRESA+"/"+auth, HttpMethod.GET, request, Void.class);

        if(re.getStatusCode() == HttpStatus.OK){
            String idToken = AuthUtils.getID(auth);
            if(!idToken.equals(id)){
                throw new myUnprocesableException("La persona autenticada no corresponde con la persona que quieres cambiar");
            }
            clientService.updateClient(id, clientInputDto);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticacion incorrecta");
    }
}
