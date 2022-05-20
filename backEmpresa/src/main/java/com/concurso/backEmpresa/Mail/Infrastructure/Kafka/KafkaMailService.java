package com.concurso.backEmpresa.Mail.Infrastructure.Kafka;

import com.concurso.backEmpresa.Mail.Domain.Mail;
import com.concurso.backEmpresa.Mail.Infrastructure.Repository.MailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaMailService {

    @Autowired
    MailRepository mailRepository;


    public void listenTopic(String action, Mail mail) {
        switch (action) {
            case "create" -> {
                mailRepository.save(mail);
                log.info("CREATE SUCCESS");
            }
            default -> {

                log.info("ERROR KAFKA SERVICE MAIL! ACCION NO ESPECIFICADA (create)");
            }
        }
    }
}
