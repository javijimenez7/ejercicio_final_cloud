package com.concurso.backEmpresa.Client.Infrastructure.Repository;

import com.concurso.backEmpresa.Client.Domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ClientRepository extends JpaRepository<Client, String> {
    Client findByEmail(String email);
}
