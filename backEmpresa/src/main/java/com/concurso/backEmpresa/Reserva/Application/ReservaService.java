package com.concurso.backEmpresa.Reserva.Application;

import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaInputDto;
import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;

import java.util.List;


public interface ReservaService {
    List<ReservaOutputDto> getAllReservas();

    ReservaOutputDto filterReservaById(String id);

    ReservaOutputDto addReserva(ReservaInputDto ReservaInputDto);

    void deleteReserva(String id);
}
