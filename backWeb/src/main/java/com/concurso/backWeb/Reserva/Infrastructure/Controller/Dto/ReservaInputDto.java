package com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class ReservaInputDto implements Serializable {
    private String details;

    @NotNull
    private String idClient;

    @NotNull
    private String idTrip;
}
