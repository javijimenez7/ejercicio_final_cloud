package com.concurso.backEmpresa.Client;

import com.concurso.backEmpresa.Client.Application.ClientServiceImpl;
import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientTest {

    @Autowired
    ClientServiceImpl clientService;

	@MockBean
    ClientRepository clientRepository;

    ClientOutputDto clientOutputDto =
            new ClientOutputDto(
                    null,
                    "admin",
                    "admiin",
                    "administrador@administrador.com",
                    "ll",
                    "323",
                    new ArrayList<>()
            );


    @Test
	void findClientByEmailTest() throws Exception {
        Client optCli = clientService.clientOutDtoToEntity(clientOutputDto);
		System.out.println("TEST1");
		when(clientRepository.findByEmail("administrador@administrador.com")).thenReturn(optCli);
		assertEquals(clientOutputDto, clientService.filterClientByEmail("administrador@administrador.com"));
		System.out.println(clientOutputDto);
	}
}
