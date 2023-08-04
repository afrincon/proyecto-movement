package com.movementapp.seguimientoproyecto.services;

import com.movementapp.seguimientoproyecto.models.DatosSuscriptor;
import com.movementapp.seguimientoproyecto.repositories.DatosSuscriptorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DatosSuscriptorService {
    private final Logger logger = LoggerFactory.getLogger(DatosSuscriptorService.class);

    private final DatosSuscriptorRepository datosSuscriptorRepository;

    public DatosSuscriptorService(DatosSuscriptorRepository datosSuscriptorRepository) {
        this.datosSuscriptorRepository = datosSuscriptorRepository;
    }

    public Mono<DatosSuscriptor> findById(Integer id) {
        return datosSuscriptorRepository.findById(id);
    }

    public Mono<DatosSuscriptor> save(DatosSuscriptor datosSuscriptor) {
        return datosSuscriptorRepository.save(datosSuscriptor);
    }

    public Mono<DatosSuscriptor> deleteById(Integer id){
        return datosSuscriptorRepository.findById(id)
                .flatMap(datosSuscriptor ->
                        datosSuscriptorRepository.deleteById(datosSuscriptor.getId()).thenReturn(datosSuscriptor));
    }

}
