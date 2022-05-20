package com.concurso.backEmpresa.Trip.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Trip.Application.TripServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("v0-empresa/trip")
public class DeleteTrip {

    @Autowired
    TripServiceImpl tripService;

    @DeleteMapping("{id}")
    public ResponseEntity deleteTrip(@PathVariable String id){
        return tripService.deleteTrip(id);
    }

}
