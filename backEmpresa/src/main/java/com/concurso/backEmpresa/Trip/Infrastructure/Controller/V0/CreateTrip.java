package com.concurso.backEmpresa.Trip.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Trip.Application.TripService;
import com.concurso.backEmpresa.Trip.Application.TripServiceImpl;
import com.concurso.backEmpresa.Trip.Domain.Trip;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripInputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;

@RestController
@RequestMapping("v0-empresa/trip")
public class CreateTrip {

    @Autowired
    TripServiceImpl tripService;

    @PostMapping
    public TripOutputDto addTrip(@RequestBody TripInputDto tripInputDto){
        return tripService.addTrip(tripInputDto);
    }
}
