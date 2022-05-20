package com.concurso.backEmpresa.Reserva.Application;

import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Repository.ClientRepository;
import com.concurso.backEmpresa.Mail.Domain.Mail;
import com.concurso.backEmpresa.Mail.Infrastructure.Repository.MailRepository;
import com.concurso.backEmpresa.Others.Exceptions.customUnprocesableException;
import com.concurso.backEmpresa.Others.Kafka.Producer.KafkaSender;
import com.concurso.backEmpresa.Others.Mail.MailSenderService;
import com.concurso.backEmpresa.Reserva.Domain.Reserva;
import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaInputDto;
import com.concurso.backEmpresa.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
import com.concurso.backEmpresa.Reserva.Infrastructure.Repository.ReservaRepository;
import com.concurso.backEmpresa.Trip.Domain.Trip;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements com.concurso.backEmpresa.Reserva.Application.ReservaService {

    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    KafkaSender sender;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    MailRepository mailRepository;

    @Value("${server.port}")
    String port;

    @Value("${topic}")
    String topic;

    @Override
    public List<ReservaOutputDto> getAllReservas(){
        List<Reserva> Reservas = reservaRepository.findAll();
        return Reservas.stream().map(ReservaOutputDto::new).collect(Collectors.toList());
    }

    @Override
    public ReservaOutputDto filterReservaById(String id){
        return new ReservaOutputDto(reservaRepository.findById(id).orElseThrow());
    }

    @Override
    public ReservaOutputDto addReserva(ReservaInputDto ReservaInputDto){

        Trip trip = tripRepository.findById(ReservaInputDto.getIdTrip()).orElseThrow();
        Client client = clientRepository.findByEmail("javijd23@gmail.com");

        if(trip.getIssue()){
            Reserva Reserva = ReservaInputToEntity(ReservaInputDto);

            ReservaOutputDto ReservaOutputDto = new ReservaOutputDto(Reserva);
            sender.sendMessage(topic, ReservaOutputDto, port, "denied", "reserva");

            throw new customUnprocesableException("Viaje cancelado. (Mantenimiento, huelga...)");
        }

        for (int i = 0; i < client.getReservas().size(); i++) {
            if(client.getReservas().get(i).getTrip().getIdTrip().equals(ReservaInputDto.getIdTrip())){

                Reserva Reserva = ReservaInputToEntity(ReservaInputDto);
                ReservaOutputDto ReservaOutputDto = new ReservaOutputDto(Reserva);
                sender.sendMessage(topic, ReservaOutputDto, port, "denied", "reserva");

                throw new customUnprocesableException("Cliente ya ha reservado para el trayecto: "+ ReservaInputDto.getIdTrip()+ " solo 1 por persona" );

            }

        }

        Reserva reserva = ReservaInputToEntity(ReservaInputDto);
        

        if(trip.getSeats()>=1){
            trip.setDecreaseSeats(1);
            reservaRepository.save(reserva);
            tripRepository.save(trip);


            TripOutputDto tripOutputDto = new TripOutputDto(trip);
            ReservaOutputDto ReservaOutputDto = new ReservaOutputDto(reserva);

            sender.sendMessage(topic, ReservaOutputDto, port, "create", "reserva");
            sender.sendMessage(topic, tripOutputDto, port, "update", "trip");

            mailSenderService.send(
                    reserva.getClient().getEmail(),
                    "Reserva Realizada",
                    "Le informamos que ha hecho una reserva para el viaje " + trip.getIdTrip() +
                            "con salida en "+ trip.getDeparture() + " y llegada en "+ trip.getArrival()
            );

            Mail mail = new Mail(trip.getDate(), trip.getDeparture(), trip.getArrival(), reserva.getClient().getEmail(), "Reserva realizada");
            mailRepository.save(mail);
            sender.sendMessage(topic, mail, port, "create", "mail");
            return ReservaOutputDto;
        }

        ReservaOutputDto reservaOutputDto = new ReservaOutputDto(reserva);
        sender.sendMessage(topic, reservaOutputDto, port, "denied", "reserva");
        throw new customUnprocesableException("No quedan asientos disponibles para este viaje");
    }

    @Override
    public void deleteReserva(String id){
        Reserva Reserva = reservaRepository.findById(id).orElseThrow();
        Trip trip = tripRepository.findById(Reserva.getTrip().getIdTrip()).orElseThrow();
        reservaRepository.delete(reservaRepository.findById(id).orElseThrow());
        trip.setIncreaseSeats(1);
        tripRepository.save(trip);

        ReservaOutputDto ReservaOutputDto = new ReservaOutputDto(Reserva);
        sender.sendMessage(topic, ReservaOutputDto, port, "delete", "reserva");

        mailSenderService.send(
                Reserva.getClient().getEmail(),
                "Reserva Cancelado",
                "Le informamos que se ha cancelado la reserva para el viaje" +
                        trip.getDeparture() + " a " + trip.getArrival() +". "
        );

        Mail mail = new Mail(trip.getDate(), trip.getDeparture(), trip.getArrival(), Reserva.getClient().getEmail(),"Reserva Cancelado");
        mailRepository.save(mail);
        sender.sendMessage(topic, mail, port, "create", "mail");

    }

    public Reserva ReservaOutputToEntity(ReservaOutputDto ReservaOutputDto){
        Reserva Reserva = new Reserva();
        Reserva.setIdReserva(ReservaOutputDto.getIdReserva());
        Reserva.setClient(clientRepository.findById(ReservaOutputDto.getIdClient()).orElseThrow());
        Reserva.setTrip(tripRepository.findById(ReservaOutputDto.getIdTrip()).orElseThrow());
        Reserva.setDetails(ReservaOutputDto.getDetails());
        return Reserva;
    }


    public Reserva ReservaInputToEntity(ReservaInputDto ReservaInputDto){
        Reserva Reserva = new Reserva();
        Reserva.setIdReserva(ReservaInputDto.getIdClient());
        Reserva.setClient(clientRepository.findById(ReservaInputDto.getIdClient()).orElseThrow());
        Reserva.setTrip(tripRepository.findById(ReservaInputDto.getIdTrip()).orElseThrow());
        Reserva.setDetails(ReservaInputDto.getDetails());
        return Reserva;
    }

    public ReservaOutputDto EntityToOutputDto(Reserva Reserva){
        return new ReservaOutputDto(Reserva);
    }
}
