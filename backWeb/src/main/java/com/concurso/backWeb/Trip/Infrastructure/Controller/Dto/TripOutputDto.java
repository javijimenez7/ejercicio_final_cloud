package com.concurso.backWeb.Trip.Infrastructure.Controller.Dto;

import com.concurso.backWeb.Trip.Domain.Trip;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class TripOutputDto implements Serializable {

    private String idTrip;

    private Date date;

    private String departure;

    private String arrival;

    private Integer seats;

    private Boolean issue;

    private List<String> Reservas;

    public TripOutputDto(Trip trip){
        setIdTrip(trip.getIdTrip());
        setDate(trip.getDate());
        setDeparture(trip.getDeparture());
        setArrival(trip.getArrival());
        setSeats(trip.getSeats());
        setIssue(trip.getIssue());

        List<String> Reservas = new ArrayList<>();
        if(trip.getReservas() != null && trip.getReservas().size()!=0){
            for (int i = 0; i < trip.getReservas().size(); i++) {
                Reservas.add(trip.getReservas().get(i).getIdReserva());
            }
        }
        setReservas(Reservas);
    }
}
