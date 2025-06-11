package br.com.fiap.newcycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication         // autoconfiguração + component scan
@EnableScheduling              // habilita @Scheduled
public class NewCycleApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewCycleApplication.class, args);
    }
}
