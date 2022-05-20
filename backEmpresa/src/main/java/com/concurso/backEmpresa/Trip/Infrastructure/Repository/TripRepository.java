package com.concurso.backEmpresa.Trip.Infrastructure.Repository;

import com.concurso.backEmpresa.Trip.Domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface TripRepository extends JpaRepository<Trip, String> {

    List<Trip> findByDepartureAndArrivalAndDate(String departure, String arrival, Date date);

    List<Trip> findByDepartureAndArrival(String departure, String arrival);

    List<Trip> findByDeparture(String departure);

}
