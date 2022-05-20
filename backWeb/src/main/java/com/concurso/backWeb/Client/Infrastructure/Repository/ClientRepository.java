package com.concurso.backWeb.Client.Infrastructure.Repository;

import com.concurso.backWeb.Client.Domain.Client;
import org.hibernate.annotations.NotFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    @NotFound
    Client findByEmail(String email);
}
