package com.concurso.backWeb.Mail.Infrastructure.Repository;

import com.concurso.backWeb.Mail.Domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MailRepository extends JpaRepository<Mail, String> {

    @Query(value = "SELECT * FROM MAIL m " +
            "WHERE m.DEPARTURE = :departure AND m.ARRIVAL = :arrival", nativeQuery = true)
    List<Mail> findByDepartureAndArrival(@Param("departure") String departure, @Param("arrival") String arrival);

    @Query(value = "SELECT * FROM MAIL m " +
            "WHERE m.DATE LIKE CONCAT(:date, '%')", nativeQuery = true)
    List<Mail> findByLocalDate(@Param("date") String date);
}
