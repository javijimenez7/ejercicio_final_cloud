package com.concurso.backWeb.Trip.Infrastructure.Controller.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
public class TripInputDto implements Serializable {

    @NotNull
    private Date date;
    @NotNull
    private String departure;
    @NotNull
    private String arrival;
    @NotNull
    private Boolean issue;

}
