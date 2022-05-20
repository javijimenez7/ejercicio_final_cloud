package com.concurso.backWeb.Reserva.Infrastructure.Controller.V0;

import com.concurso.backWeb.Reserva.Application.ReservaServiceImpl;
import com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
import com.concurso.backWeb.Reserva.Infrastructure.Repository.ReservaRepository;
import com.concurso.backWeb.Others.Auth.AuthUtils;
import com.concurso.backWeb.Others.Exceptions.myUnprocesableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("v0/reserva")


public class ReadReserva {

    @Autowired
    ReservaServiceImpl reservaService;
    @Autowired
    ReservaRepository reservaRepository;

    @Value("${urlempresa}")
    String EMPRESA;

    @GetMapping("{id}")
    public ReservaOutputDto filterReservaById(@PathVariable String id, @RequestHeader("Authorization") String auth) {
        HttpEntity<Object> request = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Void> re = new RestTemplate().exchange(EMPRESA +"/"+ auth, HttpMethod.GET, request, Void.class);

        if(re.getStatusCode() == HttpStatus.OK){
            String idToken = AuthUtils.getID(auth);
            if(!idToken.equals(reservaRepository.findById(id).orElseThrow().getClient().getIdClient())){
                throw new myUnprocesableException("La persona autenticada no coincide con el id de la persona de la que quieres leer el Reserva");
            }
            return reservaService.filterReservaById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticacion incorrecta");
        }
    }
}
