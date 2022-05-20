package com.concurso.backWeb.Auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("v0")
@RestController
public class AuthController {

    @Value("${urlempresa}")
    String empresa;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestHeader("email") String email, @RequestHeader("password") String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("email", email);
        headers.set("password", password);
        HttpEntity<Object> request = new HttpEntity<>(headers);


        return new RestTemplate().exchange(
                empresa,
                HttpMethod.POST,
                request,
                String.class
        );
    }
}
