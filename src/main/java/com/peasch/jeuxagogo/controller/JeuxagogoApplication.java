package com.peasch.jeuxagogo.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.peasch.*"})
@EntityScan(basePackages = {"com.peasch.*"})
@EnableJpaRepositories(basePackages = {"com.peasch.*"})
public class JeuxagogoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeuxagogoApplication.class, args);
    }

}
