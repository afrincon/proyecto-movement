package com.movement.proyectoseguimientoe.service;

import com.movement.proyectoseguimientoe.model.Suscripcion;
import com.movement.proyectoseguimientoe.repository.SuscripcionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SuscripcionService {

    private final Logger LOGGER = LoggerFactory.getLogger(SuscripcionService.class);

    private final SuscripcionRepository suscripcionRepository;

    public SuscripcionService(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    public Flux<Suscripcion> getAllSuscripciones() {
       return suscripcionRepository.findAll()
               .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar todos las suscripciones", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ninguna suscripcion encontrada").getMostSpecificCause()));
    }

    public Mono<Suscripcion> getSuscripcionById(Integer id) {
        return suscripcionRepository.findById(id)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar la suscripcion con id: " + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Suscriptor con id: " + id + " no encontrado").getMostSpecificCause()));
    }

    public Mono<Suscripcion> createSuscripcion(Suscripcion suscripcion) {
        suscripcion.setCreated(LocalDateTime.now());
        suscripcion.setUpdated(LocalDateTime.now());
        suscripcion.setEstado(true);
        return suscripcionRepository.save(suscripcion)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al guardar la suscripcion" + suscripcion, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscripcion no guardado").getMostSpecificCause()
                ));
    }

    public Mono<Suscripcion> uddateSuscripcion(Integer id, Suscripcion suscripcion) {
        return suscripcionRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optSuscripcion -> {
                    if(optSuscripcion.isPresent()) {
                        suscripcion.setId(id);
                        suscripcion.setIdSuscriptor(optSuscripcion.get().getIdSuscriptor());
                        if(suscripcion.getFechaInicio().equals(optSuscripcion.get().getFechaInicio())) {
                           suscripcion.setFechaInicio(optSuscripcion.get().getFechaInicio());
                        }
                        if(suscripcion.getFechaFinalizacion().equals(optSuscripcion.get().getFechaFinalizacion())) {
                           suscripcion.setFechaFinalizacion(optSuscripcion.get().getFechaFinalizacion());
                        }
                        if (suscripcion.getEstado().equals(optSuscripcion.get().getEstado())) {
                           suscripcion.setEstado(optSuscripcion.get().getEstado());
                        }
                        suscripcion.setCreated(optSuscripcion.get().getCreated());
                        suscripcion.setUpdated(LocalDateTime.now());
                        return suscripcionRepository.save(suscripcion)
                                .onErrorResume(throwable -> {
                                    LOGGER.error("Error al actualizar el suscripcion: " + suscripcion, throwable);
                                    return Mono.empty();
                                })
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Suscripcion=" + suscripcion +" no actualizado").getMostSpecificCause()));
                   }
                    return Mono.empty();
                });
    }

    public Mono<Suscripcion> deleteSuscripcionById(Integer id) {
        return suscripcionRepository.findById(id)
                .flatMap(suscriptor -> suscripcionRepository.deleteById(suscriptor.getId()).thenReturn(suscriptor))
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al borrar la suscripcion con id" + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscripcion con id: " + id + " no borrado.")
                ));
    }

    public Mono<Void> deleteAllSuscripciones() {
        return suscripcionRepository.deleteAll()
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al borrar las suscripciones", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "suscripciones no borrados").getMostSpecificCause()));
    }

    public Flux<Suscripcion> getSuscionesByEstado(Boolean estado) {
        return suscripcionRepository.findByEstado(estado)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar el sucripciones con estado: " + estado, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Suscriptor con estado: " + estado + " no encontrado").getMostSpecificCause()));

    }

}
