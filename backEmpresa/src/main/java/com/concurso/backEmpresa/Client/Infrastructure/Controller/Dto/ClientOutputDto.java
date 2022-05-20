package com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto;

import com.concurso.backEmpresa.Client.Domain.Client;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private List<String> reservas;


    public ClientOutputDto(Client client) {
        setEmail(client.getEmail());
        setSurname(client.getSurname());
        setName(client.getName());
        setPassword(client.getPassword());
        setPhone(client.getPhone());

        List<String> reservas = new ArrayList<>();
        if(client.getReservas() != null && client.getReservas().size() != 0) {
            for(int i = 0; i < client.getReservas().size(); i++){
                reservas.add(client.getReservas().get(i).getIdReserva());
            }
        }
        setReservas(reservas);

    }
}