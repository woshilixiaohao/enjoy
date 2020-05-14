package com.lxh.enjoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController("/test")
public class EnjoyApplication {



    public static void main(String[] args) {
        SpringApplication.run(EnjoyApplication.class, args);
    }

}
