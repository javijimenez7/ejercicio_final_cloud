package com.concurso.backEmpresa.Security;


import com.concurso.backEmpresa.Client.Domain.Client;
import com.concurso.backEmpresa.Client.Infrastructure.Repository.ClientRepository;
import com.concurso.backEmpresa.Others.Exceptions.customUnprocesableException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("v0-empresa/token")
public class LoginController {

    @Autowired
    ClientRepository clientRepository;

    @Value("${admin.email}")
    String admin_email;

    @PostMapping
    public ResponseEntity<String> login(@RequestHeader("email") String email, @RequestHeader("password") String pwd) throws Exception {

        Client client = clientRepository.findByEmail(email);
        if (!client.getPassword().equals(pwd)) {
            throw new customUnprocesableException("Password incorrecta");
        }

        if (admin_email.equals(client.getEmail())) {
            return new ResponseEntity<>(getJWTToken(client.getIdClient(), client.getEmail(), "ROLE_ADMIN"), HttpStatus.OK);
        }
        return new ResponseEntity<>(getJWTToken(client.getIdClient(), client.getEmail(), "ROLE_USER"), HttpStatus.OK);
    }

    @GetMapping("{token}")
    public ResponseEntity<Void> checkToken(@PathVariable String token) {
        if (this.verifyToken(token))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }


    private String getJWTToken(String id, String email, String rol) {

        String secretKey = "${secretKey}";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(rol);

        String token = Jwts
                .builder()
                .setId((id))
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    private boolean verifyToken(String token) {


        final String secretKey = "${secretKey}";
        final String prefix = "Bearer ";
        try {
            String jwToken = token.replace(prefix, "");
            Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwToken).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
