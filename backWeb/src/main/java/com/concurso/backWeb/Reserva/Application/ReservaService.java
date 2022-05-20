package com.concurso.backWeb.Reserva.Application;

import com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto.ReservaInputDto;
import com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;

import java.util.List;


public interface ReservaService {
    List<ReservaOutputDto> getAllReservas();

    ReservaOutputDto filterReservaById(String id);


    ReservaOutputDto addReserva(ReservaInputDto ReservaInputDto);
}
