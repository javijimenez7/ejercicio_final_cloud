package com.concurso.backWeb.Reserva.Application;

import com.concurso.backWeb.Client.Domain.Client;
import com.concurso.backWeb.Client.Infrastructure.Repository.ClientRepository;
import com.concurso.backWeb.Mail.Domain.Mail;
import com.concurso.backWeb.Mail.Infrastructure.Repository.MailRepository;
import com.concurso.backWeb.Reserva.Domain.Reserva;
import com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto.ReservaInputDto;
import com.concurso.backWeb.Reserva.Infrastructure.Controller.Dto.ReservaOutputDto;
import com.concurso.backWeb.Reserva.Infrastructure.Repository.ReservaRepository;
import com.concurso.backWeb.Trip.Domain.Trip;
import com.concurso.backWeb.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backWeb.Trip.Infrastructure.Repository.TripRepository;
import com.concurso.backWeb.Others.Exceptions.myUnprocesableException;
import com.concurso.backWeb.Others.Kafka.Producer.KafkaSender;
import com.concurso.backWeb.Others.Mail.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;


@Service
public class ReservaServiceImpl implements ReservaService{

    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    KafkaSender sender;
    @Autowired
    MailRepository mailRepository;
    @Autowired
    MailSenderService mailSenderService;

    @Value("${server.port}")
    private String port;

    @Value("${topic}")
    String topic;


    @Override
    public List<ReservaOutputDto> getAllReservas(){
        List<Reserva> Reservas = reservaRepository.findAll();
        return Reservas.stream().map(ReservaOutputDto::new).collect(Collectors.toList());
    }

    @Override
    public ReservaOutputDto filterReservaById(String id){
        Reserva Reserva = reservaRepository.findById(id).orElseThrow();
        return new ReservaOutputDto(Reserva);
    }

    @Override
    public ReservaOutputDto addReserva(ReservaInputDto reservaInputDto){
        Client client = clientRepository.findByEmail("javijd23@gmail.com");
        Trip trip = tripRepository.findById(reservaInputDto.getIdTrip()).orElseThrow();

        if(trip.getIssue()){
            Reserva reserva = ReservaInputToEntity(reservaInputDto);
            ReservaOutputDto ReservaOutputDto = new ReservaOutputDto(reserva);
            sender.sendMessage(topic, ReservaOutputDto, port, "denied","reserva");
            throw new myUnprocesableException("Viaje cancelado. (Mantenimiento, huelga, etc.)");
        }

        for (int i = 0; i <client.getReservas().size(); i++) {
            if(client.getReservas().get(i).getTrip().getIdTrip().equals(reservaInputDto.getIdTrip())){

                Reserva reserva = ReservaInputToEntity(reservaInputDto);
                ReservaOutputDto reservaOutputDto = new ReservaOutputDto(reserva);
                sender.sendMessage(topic, reservaOutputDto, port, "denied", "reserva");
                throw new myUnprocesableException("Cliente ya tiene Reserva para el trayecto: "+ reservaOutputDto.getIdTrip() + " solo 1 por persona");
            }
        }

        Reserva reserva = ReservaInputToEntity(reservaInputDto);


        if(trip.getSeats()>=1){
            trip.setDecreaseSeats(1);
            reservaRepository.save(reserva);
            tripRepository.save(trip);

            ReservaOutputDto ReservaOutputDto = new ReservaOutputDto(reserva);
            TripOutputDto tripOutputDto = new TripOutputDto(trip);

            sender.sendMessage(topic, ReservaOutputDto, port, "create", "reserva");
            sender.sendMessage(topic, tripOutputDto, port, "update", "trip");

            mailSenderService.sendMail(
                    reserva.getClient().getEmail(),
                    "Reserva Realizada",
                    "Le informamos que ha hecho una reserva para el viaje: " +
                            trip.getDeparture() + " a " + trip.getArrival() + ".\n" +
                            "Su identificador de viaje es " + reserva.getIdReserva()
            );

            Mail mail = new Mail(trip.getDate(),trip.getDeparture(), trip.getArrival(), reserva.getClient().getEmail(), "Reserva Realizada");
            mailRepository.save(mail);
            sender.sendMessage(topic, mail, port, "create", "mail");
            return ReservaOutputDto;
        }


        tripRepository.save(trip);
        ReservaOutputDto reservaOutputDto = new ReservaOutputDto(reserva);
        sender.sendMessage(topic, reservaOutputDto, port, "denied", "reserva");
        throw new myUnprocesableException("No quedan asientos disponibles para este trayecto");
    }

    public void deleteReserva(String id){
        Reserva reserva = reservaRepository.findById(id).orElseThrow();
        Trip trip = tripRepository.findById(reserva.getTrip().getIdTrip()).orElseThrow();
        reservaRepository.delete(reservaRepository.findById(id).orElseThrow());
        trip.setIncreaseSeats(1);
        tripRepository.save(trip);

        ReservaOutputDto reservaOutputDto = new ReservaOutputDto(reserva);
        sender.sendMessage(topic, reservaOutputDto, port, "delete", "reserva");


        mailSenderService.sendMail(
                reserva.getClient().getEmail(),
                "Reserva Cancelado",
                "Le informamos que se ha cancelado la reserva para el viaje: " +
                        trip.getDeparture() + " a " + trip.getArrival() + ".\n" +
                        "Su identificador de viaje es " + reserva.getIdReserva()
        );

        Mail mail = new Mail(trip.getDate(),trip.getDeparture(), trip.getArrival(), reserva.getClient().getEmail(), "Reserva Cancelada");
        mailRepository.save(mail);
        sender.sendMessage(topic, mail, port, "create", "mail");


    }


    public Reserva ReservaOutputToEntity(ReservaOutputDto reservaOutputDto){
        Reserva reserva = new Reserva();
        reserva.setIdReserva(reservaOutputDto.getIdReserva());
        reserva.setClient(clientRepository.findById(reservaOutputDto.getIdClient()).orElseThrow());
        reserva.setTrip(tripRepository.findById(reservaOutputDto.getIdTrip()).orElseThrow());
        reserva.setDetails(reservaOutputDto.getDetails());
        return reserva;
    }


    public Reserva ReservaInputToEntity(ReservaInputDto reservaInputDto){
        Reserva reserva = new Reserva();
        reserva.setClient(clientRepository.findById(reservaInputDto.getIdClient()).orElseThrow());
        reserva.setTrip(tripRepository.findById(reservaInputDto.getIdTrip()).orElseThrow());
        reserva.setDetails(reservaInputDto.getDetails());
        return reserva;
    }

    public ReservaOutputDto EntityToOutputDto(Reserva reserva){
        return new ReservaOutputDto(reserva);
    }

}
