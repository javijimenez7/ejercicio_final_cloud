package com.concurso.backEmpresa.Trip.Domain;

import com.concurso.backEmpresa.Reserva.Domain.Reserva;

import com.concurso.backEmpresa.StringPrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip")
public class Trip {
    @Id
    @Column(name = "id_trip", length = 32)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqT")
    @GenericGenerator(
            name = "seqT",
            strategy = "com.concurso.backEmpresa.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TRIP"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            }
    )
    private String idTrip;

    private Date date;

    private String departure;

    private String arrival;

    private Integer seats = 40;

    private Boolean issue;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

    public void setDecreaseSeats(Integer seats) {
        this.seats -= seats;
    }

    public void setIncreaseSeats(Integer seats) {
        this.seats += seats;
    }


}
