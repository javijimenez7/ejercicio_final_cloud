package com.concurso.backWeb.Trip.Infrastructure.Controller.V0;

import com.concurso.backWeb.Trip.Application.TripServiceImpl;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripDisponibleOutputDto;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("v0/trip/")

public class ReadTrip {

    @Autowired
    TripServiceImpl tripService;

    @GetMapping("{id}")
    public TripOutputDto readTripById(@PathVariable String id) {
        return tripService.filterTripById(id);
    }

    @GetMapping("/details")
    public List<TripOutputDto> findByDepartureAndArrivalAndDate(@RequestParam String departure, @RequestParam(value="arrival", required = false) String arrival, @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date) {
        return tripService.findByDepartureAndArrivalAndDate(departure, arrival, date);
    }

    @GetMapping("/disponible/{arrival}")
    public List<TripDisponibleOutputDto> findTripDisponible(@PathVariable("arrival") String arrival, @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date) {
        return tripService.findByArrival(arrival);
    }


}
