package com.concurso.backWeb.Trip.Infrastructure.Controller.Dto;


import com.concurso.backWeb.Trip.Domain.Trip;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@NoArgsConstructor
public class TripDisponibleOutputDto implements Serializable {

    private String idTrip;

    private Date date;

    private String departure;

    private String horaSalida;

    private Integer seats;

    public TripDisponibleOutputDto(Trip trip) {
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

        setIdTrip(trip.getIdTrip());
        setDate(trip.getDate());
        setDeparture(trip.getDeparture());
        setSeats(trip.getSeats());
        setHoraSalida(hourFormat.format(trip.getDate()));

    }

}
