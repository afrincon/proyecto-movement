package com.movement.modulo2.services;

import com.movement.modulo2.models.Suscriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;


    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendKey(String topico, Integer key, Suscriptor suscriptor) {
        var future = kafkaTemplate.send(topico, key.toString(), suscriptor.toString());

        future.whenComplete((resultadoEnvio, excepcion) -> {
            if(excepcion != null) {
                LOGGER.error(excepcion.getMessage());
                future.completeExceptionally(excepcion);
            } else {
                future.complete(resultadoEnvio);
            }
            LOGGER.info("Suscriptor enviado al topido de kafka con id: " + suscriptor);
        });
    }
}
