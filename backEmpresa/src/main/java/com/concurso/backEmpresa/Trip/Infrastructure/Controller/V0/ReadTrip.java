package com.concurso.backEmpresa.Trip.Infrastructure.Controller.V0;

import com.concurso.backEmpresa.Trip.Application.TripServiceImpl;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("v0-empresa/trip")
public class ReadTrip {

    @Autowired
    TripServiceImpl tripService;

    @GetMapping("{id}")
    public TripOutputDto readTripById(@PathVariable String id) {
        return tripService.filterTripById(id);
    }

    @GetMapping("/details")
    public List<TripOutputDto> findByDepartureAndArrivalAndDate(@RequestParam String departure, @RequestParam(required = false, value="arrival") String arrival, @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date) {
        return tripService.findByDepartureAndArrivalAndDate(departure, arrival, date);
    }

    @GetMapping
    public List<TripOutputDto> findAll(){
        return tripService.getAllTrips();
    }

}
