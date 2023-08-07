package com.movement.proyectoseguimientoe.service;

import com.movement.proyectoseguimientoe.model.DatosSuscriptor;
import com.movement.proyectoseguimientoe.repository.DatosSuscriptorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class DatosSuscriptorService {
    private final Logger LOGGER = LoggerFactory.getLogger(PagoService.class);

    private final DatosSuscriptorRepository datosSuscriptorRepository;

    public DatosSuscriptorService(DatosSuscriptorRepository datosSuscriptorRepository) {
        this.datosSuscriptorRepository = datosSuscriptorRepository;
    }

    public Mono<DatosSuscriptor> getDatosSuscripdorByID(Integer id) {
        return datosSuscriptorRepository.findById(id);
    }

    public Mono<DatosSuscriptor> createDatosSuscriptor(DatosSuscriptor datosSuscriptor) {
        if(validateDatosSuscriptor(datosSuscriptor)) {
            datosSuscriptor.setCreated(LocalDateTime.now());
            return datosSuscriptorRepository.save(datosSuscriptor)
                    .onErrorResume(throwable -> {
                        LOGGER.error("Error al guardar datos dl suscriptor" + datosSuscriptor, throwable);
                        return Mono.empty();
                    })
                    .switchIfEmpty(Mono.error(
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Datos del suscriptor no guardado").getMostSpecificCause()
                    ));
        }
        return Mono.empty();

    }

    public Mono<DatosSuscriptor> deleteDatosSuscripdorById(Integer id){
        return datosSuscriptorRepository.findById(id)
                .flatMap(datosSuscriptor ->
                        datosSuscriptorRepository.deleteById(datosSuscriptor.getId()).thenReturn(datosSuscriptor))
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al borrar datos del suscriptor con id" + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Datos suscriptor con id: " + id + " no borrado.")
                ));
    }

    protected Boolean validateDatosSuscriptor(DatosSuscriptor datosSuscriptor) {
        return datosSuscriptor.getAltura() >= 0 && datosSuscriptor.getPeso() >= 0.0;
    }
}
