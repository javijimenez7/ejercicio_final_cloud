package com.concurso.backWeb.Trip.Application;


import com.concurso.backWeb.Trip.Domain.Trip;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripDisponibleOutputDto;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripInputDto;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backWeb.Trip.Infrastructure.Repository.TripRepository;
import com.concurso.backWeb.Others.Kafka.Producer.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@Service

public class TripServiceImpl implements TripService{

    @Autowired
    TripRepository tripRepository;
    @Autowired
    KafkaSender sender;

    @Value("${server.port}")
    private String port;

    @Value("${topic}")
    String topic;


    @Override
    public List<TripOutputDto> getAllTrips(){
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(TripOutputDto::new).collect(Collectors.toList());
    }

    @Override
    public TripOutputDto filterTripById(String id){
        Trip trip = tripRepository.findById(id).orElseThrow();
        return new TripOutputDto(trip);
    }

    @Override
    public List<TripOutputDto> findByDepartureAndArrivalAndDate(String departure, String arrival, Date date){
        List<Trip> trips;

        if(date==null){
            if(arrival==null) {
                return tripRepository.findByDeparture(departure).stream().map(TripOutputDto::new).toList();
            }
            return tripRepository.findByDepartureAndArrival(departure, arrival).stream().map(TripOutputDto::new).toList();
        }

        return tripRepository.findByDepartureAndArrivalAndDate(departure, arrival, date).stream().map(TripOutputDto::new).toList();
    }

    @Override
    public List<TripDisponibleOutputDto> findByArrival(String arrival){

        return tripRepository.findByArrival(arrival).stream().map(TripDisponibleOutputDto::new).toList();
    }

    @Override
    public TripOutputDto addTrip(TripInputDto tripInputDto){
        Trip trip = tripInputToEntity(tripInputDto);
        if(tripInputDto.getIssue()) {trip.setSeats(0);}
        tripRepository.save(trip);

        TripOutputDto tripOutputDto = new TripOutputDto(trip);

        sender.sendMessage(topic, tripOutputDto, port, "create", "trip");
        return tripOutputDto;
    }

    @Override
    public TripOutputDto updateTrip(String id, TripInputDto tripInputDto){
        Trip trip = tripRepository.findById(id).orElseThrow();

        if (tripInputDto.getIssue()){trip.setSeats(0);}
        if (!tripInputDto.getIssue()){
            if (trip.getIssue()){
                trip.setSeats(40);
            }
        }

        trip.setDate(tripInputDto.getDate());
        trip.setArrival(tripInputDto.getArrival());
        trip.setDeparture(tripInputDto.getDeparture());
        trip.setIssue(tripInputDto.getIssue());

        tripRepository.save(trip);
        TripOutputDto tripOutputDto = entityToTripOutDto(trip);
        sender.sendMessage(topic, tripOutputDto, port, "update", "trip");
        return tripOutputDto;
    }

    @Override
    public void deleteTrip(String id){
        TripOutputDto tripOutputDto = entityToTripOutDto(tripRepository.findById(id).orElseThrow());
        tripRepository.delete(tripRepository.findById(id).orElseThrow());
        sender.sendMessage(topic, tripOutputDto, port, "delete", "trip");
    }

    public Trip tripInputToEntity(TripInputDto tripInputDto){
        Trip trip = new Trip();
        trip.setArrival(tripInputDto.getArrival());
        trip.setDate(tripInputDto.getDate());
        trip.setDeparture(tripInputDto.getDeparture());
        trip.setIssue(tripInputDto.getIssue());

        return trip;
    }

    public Trip tripOutDtoToEntity(TripOutputDto tripOutputDto){
        Trip trip = new Trip();
        trip.setIdTrip(tripOutputDto.getIdTrip());
        trip.setSeats(tripOutputDto.getSeats());
        trip.setDeparture(tripOutputDto.getDeparture());
        trip.setDate(tripOutputDto.getDate());
        trip.setArrival(tripOutputDto.getArrival());
        trip.setIssue(tripOutputDto.getIssue());
        return trip;
    }


    public TripOutputDto entityToTripOutDto(Trip trip){
        TripOutputDto tripOutputDto = new TripOutputDto(trip);
        return tripOutputDto;
    }
}
