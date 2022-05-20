package com.concurso.backEmpresa.Mail.Infrastructure.Repository;

import com.concurso.backEmpresa.Mail.Domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MailRepository extends JpaRepository<Mail, String> {

    List<Mail> findByDepartureAndArrival(String departure, String arrival);
}
