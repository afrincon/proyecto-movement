package com.movement.modulo2;

import com.movement.modulo2.models.Suscriptor;
import com.movement.modulo2.services.KafkaProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Modulo2Application implements CommandLineRunner {

    private final KafkaProducerService kafkaProducerService;

    public Modulo2Application(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Modulo2Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        producerData();
    }

    private void producerData(){
        Suscriptor s1 = new Suscriptor(1, "Carlos", "123456", "M", "Avenida siempre viva 123", "123456");
        String topico = "suscriptores-2023";
        kafkaProducerService.sendKey(topico, s1.getId(), s1);
    }
}
