package com.concurso.backEmpresa.Reserva.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Others.Auth.AuthUtils;
import com.concurso.backEmpresa.Others.Exceptions.customUnprocesableException;
import com.concurso.backEmpresa.Reserva.Application.ReservaServiceImpl;
import com.concurso.backEmpresa.Reserva.Infrastructure.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("v0-empresa/reserva")
public class DeleteReseva {

    @Autowired
    ReservaServiceImpl reservaService;
    @Autowired
    ReservaRepository reservaRepository;

    @DeleteMapping("{id}")
    public void deleteReserva(@PathVariable String id,@RequestHeader("Authorization") String auth ){
        String idToken = AuthUtils.getID(auth);
        if(!idToken.equals(reservaRepository.findById(id).orElseThrow().getClient().getIdClient())){
            throw new customUnprocesableException("No puedes eliminar la reserva de otra persona, solo la tuya");
        }
        reservaService.deleteReserva(id);
    }
}
