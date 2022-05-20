package com.concurso.backWeb.Client.Domain;

import com.concurso.backWeb.Reserva.Domain.Reserva;
import com.concurso.backWeb.StringPrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client {


    @Id
    @Column(name = "id_client", length = 32)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqC")
    @GenericGenerator(
            name = "seqC",
            strategy = "com.concurso.backWeb.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "CLI"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            }
    )

    private String idClient;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> Reservas;

}
