package com.concurso.backEmpresa.Reserva.Infrastructure.Repository;

import com.concurso.backEmpresa.Reserva.Domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ReservaRepository extends JpaRepository<Reserva, String> {
}
