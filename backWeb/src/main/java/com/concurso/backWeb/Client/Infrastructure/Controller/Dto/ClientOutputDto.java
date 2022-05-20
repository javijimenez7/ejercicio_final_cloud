package com.concurso.backWeb.Client.Infrastructure.Controller.Dto;

import com.concurso.backWeb.Client.Domain.Client;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class ClientOutputDto implements Serializable {

    private String idClient;

    @NotNull
    private String name;

    private String surname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String phone;

    private List<String> Reservas;


    public ClientOutputDto(Client client) {
        setIdClient(client.getIdClient());
        setEmail(client.getEmail());
        setSurname(client.getSurname());
        setName(client.getName());
        setPassword(client.getPassword());
        setPhone(client.getPhone());

        List<String> Reservas = new ArrayList<>();
        if(client.getReservas() != null && client.getReservas().size() != 0) {
            for(int i = 0; i < client.getReservas().size(); i++){
                Reservas.add(client.getReservas().get(i).getIdReserva());
            }
        }
        setReservas(Reservas);

    }
}