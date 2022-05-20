package com.concurso.backEmpresa;

import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Repository.ClientRepository;
import com.concurso.backEmpresa.Others.Kafka.Producer.KafkaSender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
@AllArgsConstructor
public class InicioAplicacion implements CommandLineRunner {


    private final ClientRepository clientRepository;
     @Autowired KafkaSender sender;



    @Override
    public void run(String... args) throws Exception {

        Client c1 = new Client("admin", "admin", "administrador@administrador.com", "1234", "123456", null);
        Client c2 = new Client("user", "user", "javijd23@gmail.com", "1234", "123456", null);

        if (clientRepository.findByEmail(c1.getEmail()) == null && clientRepository.findByEmail(c2.getEmail()) == null){

            clientRepository.save(c1);
            clientRepository.save(c2);

            sender.sendMessage("bus", new ClientOutputDto(c1), "8090", "create", "client");
            sender.sendMessage("bus", new ClientOutputDto(c2), "8090", "create", "client");
        }

    }
}
