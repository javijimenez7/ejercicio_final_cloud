package com.concurso.backEmpresa.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static javax.ws.rs.HttpMethod.*;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    String ADMIN = "ADMIN";
    String USER = "USER";

    String TRIP = "/v0-empresa/trip/**";
    String CLIENT = "/v0-empresa/client/**";
    String RESERVA = "/v0-empresa/reserva/**";
    String MAIL = "/v0-empresa/mail/**";

    String TOKEN = "/v0-empresa/token/**";

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests();

        http.authorizeRequests().antMatchers(POST, TOKEN).permitAll();




        http.authorizeRequests().antMatchers(POST, TRIP).hasRole("ADMIN");
        http.authorizeRequests().antMatchers(GET, TRIP).hasRole("ADMIN");
        http.authorizeRequests().antMatchers(PUT, TRIP).hasRole("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, TRIP).hasRole("ADMIN");

        http.authorizeRequests().antMatchers(POST, RESERVA).hasRole("USER");
        http.authorizeRequests().antMatchers(GET, RESERVA).hasRole("USER");
        http.authorizeRequests().antMatchers(GET, RESERVA).hasRole("ADMIN");

        http.authorizeRequests().antMatchers(POST, CLIENT).permitAll();
        http.authorizeRequests().antMatchers(GET, CLIENT).hasRole("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, CLIENT).hasRole("ADMIN");

        http.authorizeRequests().antMatchers(GET, MAIL).hasRole("ADMIN");


        http.authorizeRequests().anyRequest().authenticated();
    }
}
