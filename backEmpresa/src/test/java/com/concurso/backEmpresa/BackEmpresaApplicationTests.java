package com.concurso.backEmpresa;

import com.concurso.backEmpresa.Client.Application.ClientServiceImpl;
import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Controller.Dto.ClientOutputDto;
import com.concurso.backEmpresa.Client.Infrastructure.Repository.ClientRepository;
import com.concurso.backEmpresa.Trip.Application.TripServiceImpl;
import com.concurso.backEmpresa.Trip.Domain.Trip;
import com.concurso.backEmpresa.Trip.Infrastructure.Controller.Dto.TripOutputDto;
import com.concurso.backEmpresa.Trip.Infrastructure.Repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BackEmpresaApplicationTests {



}
