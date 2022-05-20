package com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto;

import com.concurso.backWeb.Reserva.Domain.Reserva;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class ReservaOutputDto implements Serializable {
    private String idReserva;

    private String details;

    @NotNull
    private String idClient;

    @NotNull
    private String idTrip;

    public ReservaOutputDto(Reserva Reserva){
        setIdReserva(Reserva.getIdReserva());
        setDetails(Reserva.getDetails());
        setIdClient(Reserva.getClient().getIdClient());
        setIdTrip(Reserva.getTrip().getIdTrip());
    }
}
