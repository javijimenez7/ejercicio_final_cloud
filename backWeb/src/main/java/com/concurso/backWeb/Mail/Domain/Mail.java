package com.concurso.backWeb.Mail.Domain;

import com.concurso.backWeb.StringPrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email")
public class Mail {

    @Id @Column(name = "id_mail", length = 32)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqM")
    @GenericGenerator(
            name = "seqM",
            strategy = "com.concurso.backWeb.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "MAIL"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            }
    )
    private String idMail;

    private Date date;

    private String departure;

    private String arrival;

    @Column(name = "email_to")
    private String emailto;

    private String subject;


    public Mail(Date date, String departure, String arrival, String email, String reserva_realizada) {
        this.date = date;
        this.departure = departure;
        this.arrival = arrival;
        this.emailto = email;
        this.subject = reserva_realizada;
    }
}
