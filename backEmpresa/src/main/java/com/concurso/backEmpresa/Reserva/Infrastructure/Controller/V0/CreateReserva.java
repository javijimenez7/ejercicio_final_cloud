package com.concurso.backEmpresa.Reserva.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Others.Auth.AuthUtils;
import com.concurso.backEmpresa.Others.Exceptions.customUnprocesableException;
import com.concurso.backEmpresa.Reserva.Application.ReservaServiceImpl;
import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaInputDto;
import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("v0-empresa/reserva")
public class CreateReserva {

    @Autowired
    ReservaServiceImpl reservaService;

    @PostMapping
    public ResponseEntity addTicket(@RequestBody ReservaInputDto reservaInputDto, @RequestHeader("Authorization") String auth) {
        String idToken = AuthUtils.getID(auth);
        if (!idToken.equals(reservaInputDto.getIdClient())) {
            throw new customUnprocesableException("No puedes comprar un ticket para una persona con un id diferente al que estas autenticado");
        }

        try {
            return new ResponseEntity<ReservaOutputDto>(reservaService.addReserva(reservaInputDto), HttpStatus.OK);
        } catch (customUnprocesableException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
