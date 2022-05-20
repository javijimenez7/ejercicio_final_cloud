package com.concurso.backEmpresa.Trip.Infrastructure.Kafka;

import com.concurso.backEmpresa.Trip.Application.TripServiceImpl;
import com.concurso.backEmpresa.Trip.Domain.Trip;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Repository.TripRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaTripService {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    TripServiceImpl tripService;

    public void listenTopic(String action, TripOutputDto tripOutputDto){
        switch (action){

            case "create" -> {
                Trip trip = tripService.tripOutDtoToEntity(tripOutputDto);
                tripRepository.save(trip);
                log.info("CREATE SUCCESS");
            }

            case "update" -> {
                Trip trip = tripRepository.findById(tripOutputDto.getIdTrip()).orElseThrow();
                trip.setArrival(tripOutputDto.getArrival());
                trip.setDeparture(tripOutputDto.getDeparture());
                trip.setIssue(tripOutputDto.getIssue());
                trip.setDate(tripOutputDto.getDate());
                trip.setSeats(tripOutputDto.getSeats());

                tripRepository.save(trip);
                log.info("UPDATE SUCCESS");
            }

            case "delete" -> {
                tripRepository.delete(tripRepository.findById(tripOutputDto.getIdTrip()).orElseThrow());
                log.info("DELETE SUCCESS");
            }
        }
    }
}
