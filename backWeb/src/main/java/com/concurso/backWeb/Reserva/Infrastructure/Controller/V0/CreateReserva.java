package com.concurso.backWeb.Reserva.Infrastructure.Controller.V0;

import com.concurso.backWeb.Reserva.Application.ReservaServiceImpl;
import com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto.ReservaInputDto;
import com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
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


public class CreateReserva {

    @Autowired
    ReservaServiceImpl reservaService;

    @Value("${urlempresa}")
    String EMPRESA;

    @PostMapping
    public ResponseEntity addReserva(@RequestBody ReservaInputDto reservaInputDto, @RequestHeader("Authorization") String auth){
        HttpEntity<Object> request = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Void> re = new RestTemplate().exchange(EMPRESA+"/"+auth, HttpMethod.GET, request, Void.class);

        if(re.getStatusCode()== HttpStatus.OK){
            String idToken = AuthUtils.getID(auth);

            if(!idToken.equals(reservaInputDto.getIdClient())){
                throw new myUnprocesableException("No puedes adquirir una reserva para una persona con id diferente al que estas autenticado");
            }

            try {
                return new ResponseEntity<ReservaOutputDto>(reservaService.addReserva(reservaInputDto), HttpStatus.OK);
            } catch (myUnprocesableException e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autorizacion incorrecta");
        }
    }
}
