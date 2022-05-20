package com.concurso.backEmpresa;

import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Properties;


@EnableEurekaClient
@SpringBootApplication
public class BackEmpresaApplication {




	public static void main(String[] args) {
		SpringApplication.run(BackEmpresaApplication.class, args);


	}

}
