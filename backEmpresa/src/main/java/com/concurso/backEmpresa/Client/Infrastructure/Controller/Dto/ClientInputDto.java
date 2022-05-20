package com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ClientInputDto implements Serializable {


    @NotNull
    private String name;

    private String surname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String phone;
}
