package com.concurso.backEmpresa.Trip.Application;

import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripInputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;


public interface TripService {
    List<TripOutputDto> getAllTrips();

    TripOutputDto filterTripById(String id);

    TripOutputDto addTrip(TripInputDto tripInputDto);

    TripOutputDto updateTrip(String id, TripInputDto tripInputDto);

    List<TripOutputDto> findByDepartureAndArrivalAndDate(String departure, String arrival, Date date);

    ResponseEntity deleteTrip(String id);
}
