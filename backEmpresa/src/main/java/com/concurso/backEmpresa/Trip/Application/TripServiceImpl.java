package com.concurso.backEmpresa.Trip.Application;

import com.concurso.backEmpresa.Mail.Domain.Mail;
import com.concurso.backEmpresa.Mail.Infrastructure.Repository.MailRepository;
import com.concurso.backEmpresa.Others.Exceptions.customUnprocesableException;
import com.concurso.backEmpresa.Reserva.Application.ReservaServiceImpl;
import com.concurso.backEmpresa.Reserva.Infrastructure.Repository.ReservaRepository;
import com.concurso.backEmpresa.Trip.Domain.Trip;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripInputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Repository.TripRepository;
import com.concurso.backEmpresa.Others.Kafka.Producer.KafkaSender;
import com.concurso.backEmpresa.Others.Mail.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    MailRepository mailRepository;
    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    KafkaSender sender;
    @Autowired
    MailSenderService mailSenderService;

    @Value("${server.port}")
    String port;

    @Value("${topic}")
    String topic;

    @Override
    public List<TripOutputDto> getAllTrips(){
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(TripOutputDto::new).collect(Collectors.toList());
    }

    @Override
    public TripOutputDto filterTripById(String id){
        return new TripOutputDto(tripRepository.findById(id).orElseThrow());
    }

    @Override
    public TripOutputDto addTrip(TripInputDto tripInputDto){
        Trip trip = tripInputToEntity(tripInputDto);
        if(tripInputDto.getIssue()){trip.setSeats(0);}
    
        tripRepository.save(trip);

        TripOutputDto tripOutputDto = entityToTripOutDto(trip);
        sender.sendMessage(topic, tripOutputDto, port, "create", "trip");
        return tripOutputDto;
    }

    @Override
    public TripOutputDto updateTrip(String id, TripInputDto tripInputDto){
        Trip trip = tripRepository.findById(id).orElseThrow();

        if(tripInputDto.getIssue()) {
            trip.setSeats(0);

            for (int i = 0; i < trip.getReservas().size(); i++) {
                mailSenderService.send(
                        trip.getReservas().get(i).getClient().getEmail(),
                        "Aviso importante. Su viaje ha sido cancelado",
                        "Le informamos que su viaje desde " + trip.getDeparture() + " a " + trip.getArrival() + " para la fecha " + trip.getDate() +
                                " ha sido cancelado de manera forzosa por huelga, accidente u otro motivo excepcional.\n" +
                                " El identificador del reserva cancelado es " + trip.getReservas().get(i).getIdReserva());

                Mail mail = new Mail(trip.getDate(), trip.getDeparture(), trip.getArrival(), trip.getReservas().get(i).getClient().getEmail(), "Cancelacion forzosa");
                mailRepository.save(mail);
                sender.sendMessage(topic, mail, port, "create", "mail");
            }
        }

        if(!tripInputDto.getIssue() && trip.getIssue()){
            trip.setSeats(40);
        }

        trip.setArrival(tripInputDto.getArrival());
        trip.setDeparture(tripInputDto.getDeparture());
        trip.setDate(tripInputDto.getDate());
        trip.setIssue(tripInputDto.getIssue());

        tripRepository.save(trip);

        TripOutputDto tripOutputDto = entityToTripOutDto(trip);
        sender.sendMessage(topic, tripOutputDto, port, "update", "trip");
        return tripOutputDto;
    }

    @Override
    public List<TripOutputDto> findByDepartureAndArrivalAndDate(String departure, String arrival, Date date){

        if(date==null){
            if(arrival==null) {
                return tripRepository.findByDeparture(departure).stream().map(TripOutputDto::new).toList();
            }
            return tripRepository.findByDepartureAndArrival(departure, arrival).stream().map(TripOutputDto::new).toList();
        }


        return tripRepository.findByDepartureAndArrivalAndDate(departure, arrival, date).stream().map(TripOutputDto::new).toList();



    }

    @Override
    public ResponseEntity deleteTrip(String id){
        TripOutputDto tripOutputDto = entityToTripOutDto(tripRepository.findById(id).orElseThrow());

        if(tripRepository.findById(id).orElseThrow().getReservas().size()>=1){
            return new ResponseEntity<>("No puede eliminar el viaje puesto que tiene reservas asignadas", HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            tripRepository.delete(tripRepository.findById(id).orElseThrow());
            sender.sendMessage(topic, tripOutputDto, port, "delete", "trip");
            return new ResponseEntity<>("Trip borrado",HttpStatus.OK);
        }
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
