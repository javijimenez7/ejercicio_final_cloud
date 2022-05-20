package com.concurso.backWeb.Trip.Infrastructure.Repository;

import com.concurso.backWeb.Trip.Domain.Trip;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface TripRepository extends JpaRepository<Trip, String> {

    List<Trip> findByDepartureAndArrivalAndDate(String departure, String arrival, Date date);

    List<Trip> findByDepartureAndArrival(String departure, String arrival);

    List<Trip> findByDeparture(String departure);

    List<Trip> findByArrival(String arrival);

    @Query(value = "SELECT * FROM TRIP t " +
            "WHERE t.DEPARTURE = :departure AND t.ARRIVAL = :arrival AND t.DATE LIKE CONCAT(:date, '%')", nativeQuery = true)
    List<Trip>findByDepartureAndArrivalAndLocalDate(@Param("departure")String departure, @Param("arrival") String arrival, @Param("date") String date);


}
