package com.concurso.backWeb.Reserva.Infrastructure.Repository;

import com.concurso.backWeb.Reserva.Domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ReservaRepository extends JpaRepository<Reserva, String> {
}
