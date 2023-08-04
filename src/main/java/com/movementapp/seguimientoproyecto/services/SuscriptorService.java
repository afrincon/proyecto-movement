package com.movementapp.seguimientoproyecto.services;

import com.movementapp.seguimientoproyecto.models.Suscriptor;
import com.movementapp.seguimientoproyecto.repositories.SuscriptorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SuscriptorService {

    private final Logger logger = LoggerFactory.getLogger(SuscriptorService.class);

    private final SuscriptorRepository suscriptorRepository;

    public SuscriptorService(SuscriptorRepository suscriptorRepository) {
        this.suscriptorRepository = suscriptorRepository;
    }

    public Flux<Suscriptor> findAll() {
        return suscriptorRepository.findAll();
    }

    public Mono<Suscriptor> findById(Integer id) {
        return suscriptorRepository.findById(id)
                .onErrorResume(throwable -> {
                    logger.error("Error al consulta el suscriptor con id: " + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Suscriptor con id: " + id + " no encontrado").getMostSpecificCause()));
    }

    public Flux<Suscriptor> findByEstado(Boolean estado) {
        return suscriptorRepository.findByEstado(estado);
    }

    public Flux<Suscriptor> findByNombreByEstado(String nombre, Boolean estado) {
        return suscriptorRepository.findByNombreAndEstado(nombre,estado);
    }

    public Mono<Suscriptor> save(Suscriptor suscriptor) {
        return suscriptorRepository.save(suscriptor);
    }

    public Mono<Suscriptor> update(Integer id, Suscriptor suscriptor) {
        return suscriptorRepository.save(suscriptor);
    }

    public Mono<Suscriptor> deleteById(Integer id){
        return suscriptorRepository.findById(id)
                .flatMap(suscriptor -> suscriptorRepository.deleteById(suscriptor.getId()).thenReturn(suscriptor));
    }

    public Mono<Void> deleteAll() {
        return suscriptorRepository.deleteAll();
    }
}
