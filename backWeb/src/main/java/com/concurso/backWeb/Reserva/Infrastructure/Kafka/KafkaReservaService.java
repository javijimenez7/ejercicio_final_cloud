package com.concurso.backWeb.Reserva.Infrastructure.Kafka;

import com.concurso.backWeb.Client.Infrastructure.Repository.ClientRepository;
import com.concurso.backWeb.Reserva.Application.ReservaServiceImpl;
import com.concurso.backWeb.Reserva.Domain.Reserva;
import com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
import com.concurso.backWeb.Reserva.Infrastructure.Repository.ReservaRepository;
import com.concurso.backWeb.Trip.Domain.Trip;
import com.concurso.backWeb.Trip.Infrastructure.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class KafkaReservaService {

    @Autowired
    ReservaServiceImpl reservaService;
    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TripRepository tripRepository;

    public void listenTopic(String action, ReservaOutputDto reservaOutputDto){
        switch (action){
            case "create" ->{
                Reserva reserva = reservaService.ReservaOutputToEntity(reservaOutputDto);
                System.out.println(reserva);
                Trip trip = tripRepository.findById(reserva.getTrip().getIdTrip()).orElseThrow();
                trip.setDecreaseSeats(1);
                reservaRepository.save(reserva);
                tripRepository.save(trip);
                System.out.println("CREATE SUCCESS");
            }

            case "update" -> {
                System.out.println("ReservaS CAN´T BE UPDATED");
            }

            case "delete" -> {
                reservaRepository.delete(reservaRepository.findById(reservaOutputDto.getIdReserva()).orElseThrow());
                Trip trip = tripRepository.findById(reservaOutputDto.getIdTrip()).orElseThrow();
                trip.setIncreaseSeats(1);
                tripRepository.save(trip);
                System.out.println("DELETE SUCCESS");
            }

            case "denied" -> {
                Trip trip = tripRepository.findById(reservaOutputDto.getIdTrip()).orElseThrow();
                tripRepository.save(trip);
                System.out.println("DENIED COUNTER INCREASED SUCCESSFULLY");
            }
        }
    }
}
