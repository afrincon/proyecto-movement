package com.movementapp.seguimientoproyecto.services;

import com.movementapp.seguimientoproyecto.models.Suscripcion;
import com.movementapp.seguimientoproyecto.repositories.SuscripcionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SuscripcionService {

    private final Logger logger = LoggerFactory.getLogger(SuscripcionService.class);

    private final SuscripcionRepository suscripcionRepository;

    public SuscripcionService(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    public Flux<Suscripcion> findAll() {
        return suscripcionRepository.findAll();
    }

    public Mono<Suscripcion> findById(Integer id) {
        return suscripcionRepository.findById(id);
    }

    public Flux<Suscripcion> findByEstado(Boolean estado) {
        return suscripcionRepository.findByEstado(estado);
    }

    public Mono<Suscripcion> save(Suscripcion suscripcion) {
        return suscripcionRepository.save(suscripcion);
    }

    public Mono<Suscripcion> update(Integer id, Suscripcion suscripcion) {
        return suscripcionRepository.save(suscripcion);
    }

    public Mono<Suscripcion> deleteById(Integer id) {
        return suscripcionRepository.findById(id)
                .flatMap(suscripcion -> suscripcionRepository.deleteById(suscripcion.getId()).thenReturn(suscripcion));
    }

    public Mono<Void> deleteAll() {
        return suscripcionRepository.deleteAll();
    }
}
