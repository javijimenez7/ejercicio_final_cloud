package com.concurso.backEmpresa.Trip.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Others.Auth.AuthUtils;
import com.concurso.backEmpresa.Others.Exceptions.customUnprocesableException;
import com.concurso.backEmpresa.Trip.Application.TripServiceImpl;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripInputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("v0-empresa/trip")
public class UpdateTrip {

    @Autowired
    TripServiceImpl tripService;

    @PutMapping("{id}")
    public TripOutputDto updateClient(@PathVariable String id, @RequestBody TripInputDto clientInputDto, @RequestHeader("Authorization") String auth){

       return tripService.updateTrip(id, clientInputDto);

    }
}
