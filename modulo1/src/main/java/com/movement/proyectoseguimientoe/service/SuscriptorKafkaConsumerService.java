package com.movement.proyectoseguimientoe.service;

import com.movement.proyectoseguimientoe.config.KafkaConfig;
import com.movement.proyectoseguimientoe.model.Suscriptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SuscriptorKafkaConsumerService {

    private final Logger LOGGER = LoggerFactory.getLogger(SuscriptorKafkaConsumerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public SuscriptorKafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String                                                                                                                                                                                                                            obtenerSuscripcion(String topico) {
        ConsumerRecord<String, String> ultimoSuscriptor;
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaTemplate.setConsumerFactory(kafkaConfig.consumerFactory());
        ultimoSuscriptor = kafkaTemplate.receive(topico, 0, 0);
        String suscriptorRecibido = Objects.requireNonNull(ultimoSuscriptor.value());
        return suscriptorRecibido;
    }
}
