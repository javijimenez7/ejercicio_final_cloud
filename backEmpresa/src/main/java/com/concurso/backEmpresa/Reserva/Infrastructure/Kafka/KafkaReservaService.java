package com.concurso.backEmpresa.Reserva.Infrastructure.Kafka;

import com.concurso.backEmpresa.Reserva.Application.ReservaServiceImpl;
import com.concurso.backEmpresa.Reserva.Domain.Reserva;
import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
import com.concurso.backEmpresa.Reserva.Infrastructure.Repository.ReservaRepository;
import com.concurso.backEmpresa.Trip.Domain.Trip;
import com.concurso.backEmpresa.Trip.Infrastructure.Repository.TripRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaReservaService {

    private final ReservaRepository ReservaRepository;
    private final TripRepository tripRepository;
    private final ReservaServiceImpl ReservaService;

    public void listenTopic(String action, ReservaOutputDto ReservaOutputDto){
        switch (action){
            case "create" -> {
                Reserva Reserva = ReservaService.ReservaOutputToEntity(ReservaOutputDto);

                Trip trip = tripRepository.findById(ReservaOutputDto.getIdTrip()).orElseThrow();
                tripRepository.save(trip);
                ReservaRepository.save(Reserva);

                log.info("CREATE SUCCESS");
            }

            case "update" -> {
                log.info("CAN´T BE MODIFIED");
            }

            case "delete" -> {
                ReservaRepository.delete(ReservaRepository.findById(ReservaOutputDto.getIdReserva()).orElseThrow());
                Trip trip = tripRepository.findById(ReservaOutputDto.getIdTrip()).orElseThrow();
                trip.setIncreaseSeats(1);
                tripRepository.save(trip);
                log.info("DELETE SUCCESS");

            }

            case "denied" -> {
                Trip trip =tripRepository.findById(ReservaOutputDto.getIdTrip()).orElseThrow();
                tripRepository.save(trip);
                log.info("SUCCESS");
            }
        }
    }
}
