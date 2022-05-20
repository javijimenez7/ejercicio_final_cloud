package com.concurso.backWeb.Trip.Infrastructure.Kafka;

import com.concurso.backWeb.Client.Domain.Client;
import com.concurso.backWeb.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backWeb.Trip.Application.TripServiceImpl;
import com.concurso.backWeb.Trip.Domain.Trip;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backWeb.Trip.Infrastructure.Repository.TripRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
                System.out.println(trip);
                tripRepository.save(trip);
                System.out.println("CREATE SUCCESS");
            }

            case "update" -> {
                Trip trip = tripRepository.findById(tripOutputDto.getIdTrip()).orElseThrow();
                trip.setDeparture(tripOutputDto.getDeparture());
                trip.setArrival(tripOutputDto.getArrival());
                trip.setIssue(tripOutputDto.getIssue());
                trip.setDate(tripOutputDto.getDate());
                trip.setSeats(tripOutputDto.getSeats());

                tripRepository.save(trip);
                System.out.println("UPDATE SUCCESS");
            }

            case "delete" -> {
                tripRepository.delete(tripRepository.findById(tripOutputDto.getIdTrip()).orElseThrow());
                System.out.println("DELETE SUCCESS");
            }


        }
    }
}
