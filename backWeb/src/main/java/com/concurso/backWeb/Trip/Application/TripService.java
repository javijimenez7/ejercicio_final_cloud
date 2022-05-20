package com.concurso.backWeb.Trip.Application;

import com.concurso.backWeb.Client.Domain.Client;
import com.concurso.backWeb.Trip.Domain.Trip;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripDisponibleOutputDto;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripInputDto;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripOutputDto;

import java.util.Date;
import java.util.List;


public interface TripService {
    List<TripOutputDto> getAllTrips();

    abstract TripOutputDto filterTripById(String id);

    List<TripOutputDto> findByDepartureAndArrivalAndDate(String departure, String arrival, Date date);

    List<TripDisponibleOutputDto> findByArrival(String arrival);

    TripOutputDto addTrip(TripInputDto tripInputDto);

    TripOutputDto updateTrip(String id, TripInputDto tripInputDto);

    void deleteTrip(String id);
}
