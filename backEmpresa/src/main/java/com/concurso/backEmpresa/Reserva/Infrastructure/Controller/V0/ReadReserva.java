package com.concurso.backEmpresa.Reserva.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Others.Auth.AuthUtils;
import com.concurso.backEmpresa.Others.Exceptions.customUnprocesableException;
import com.concurso.backEmpresa.Reserva.Application.ReservaServiceImpl;
import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
import com.concurso.backEmpresa.Reserva.Infrastructure.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v0-empresa/reserva")


public class ReadReserva {

    @Autowired
    ReservaServiceImpl reservaService;
    @Autowired
    ReservaRepository reservaRepository;


    @GetMapping("{id}")
    public ReservaOutputDto filterReservaById(@PathVariable String id, @RequestHeader("Authorization") String auth) {

        String idToken = AuthUtils.getID(auth);
        if(!idToken.equals(reservaRepository.findById(id).orElseThrow().getClient().getIdClient())){
            throw new customUnprocesableException("La persona autenticada no coincide con el id de la persona de la que quieres leer el Reserva");
        }
        return reservaService.filterReservaById(id);
    }

    @GetMapping
    public List<ReservaOutputDto> findAll(){
        return reservaService.getAllReservas();
    }

}
