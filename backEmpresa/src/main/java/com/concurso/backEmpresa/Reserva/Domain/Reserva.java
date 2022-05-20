package com.concurso.backEmpresa.Reserva.Domain;

import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.StringPrefixedSequenceIdGenerator;
import com.concurso.backEmpresa.Trip.Domain.Trip;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Data
@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @Column(name = "id_Reserva", length = 32)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqR")
    @GenericGenerator(
            name = "seqR",
            strategy = "com.concurso.backEmpresa.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "RESERVA"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            }
    )
    private String idReserva;

    private String details;

    @ManyToOne @JoinColumn(name = "fk_client") @EqualsAndHashCode.Exclude @ToString.Exclude
    private Client client;

    @ManyToOne @JoinColumn(name = "fk_trip") @EqualsAndHashCode.Exclude @ToString.Exclude
    private Trip trip;

}
