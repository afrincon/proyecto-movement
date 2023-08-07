package com.movement.proyectoseguimientoe.service;

import com.movement.proyectoseguimientoe.model.Suscriptor;
import com.movement.proyectoseguimientoe.repository.SuscriptorRepository;
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
public class SuscriptorService {

    private final Logger LOGGER = LoggerFactory.getLogger(SuscriptorService.class);

    private final SuscriptorRepository suscriptorRepository;

    public SuscriptorService(SuscriptorRepository suscriptorRepository) {
        this.suscriptorRepository = suscriptorRepository;
    }

    public Flux<Suscriptor> getAllSuscriptors() {
        return suscriptorRepository.findAll()
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar todos los suscriptores", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ningun Suscriptor encontrado").getMostSpecificCause()));
    }

    public Mono<Suscriptor> getSuscriptorById(Integer id) {
        return suscriptorRepository.findById(id)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar el suscriptor con id: " + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Suscriptor con id: " + id + " no encontrado").getMostSpecificCause()));
    }

    public Mono<Suscriptor> saveSuscriptor(Suscriptor suscriptor) {
        if(validarNombre(suscriptor)) {
            suscriptor.setEstado(true);
            suscriptor.setCreated(LocalDateTime.now());
            suscriptor.setUpdated(LocalDateTime.now());
            return suscriptorRepository.save(suscriptor)
                    .onErrorResume(throwable -> {
                        LOGGER.error("Error al guardar al suscriptor" + suscriptor, throwable);
                        return Mono.empty();
                    })
                    .switchIfEmpty(Mono.error(
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscriptor no guardado").getMostSpecificCause()
                    ));
        }
        return Mono.empty();
    }

    public Mono<Suscriptor> updateSuscriptor(Integer id, Suscriptor suscriptor) {
        return suscriptorRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optSuscriptor -> {
                    if (optSuscriptor.isPresent()) {
                        suscriptor.setId(id);
                        if (suscriptor.getNombre().equals(optSuscriptor.get().getNombre())) {
                            suscriptor.setNombre(optSuscriptor.get().getNombre());
                        }
                        suscriptor.setIdentificacion(optSuscriptor.get().getIdentificacion());
                        if (suscriptor.getSexo().equals(optSuscriptor.get().getSexo())) {
                            suscriptor.setSexo(optSuscriptor.get().getSexo());
                        }
                        if (suscriptor.getDireccion().equals(optSuscriptor.get().getDireccion())) {
                            suscriptor.setDireccion(optSuscriptor.get().getDireccion());
                        }
                        if (suscriptor.getTelefono().equals(optSuscriptor.get().getTelefono())) {
                            suscriptor.setTelefono(optSuscriptor.get().getTelefono());
                        }
                        if (suscriptor.getEstado().equals(optSuscriptor.get().getEstado())) {
                            suscriptor.setEstado(optSuscriptor.get().getEstado());
                        }
                        suscriptor.setCreated(optSuscriptor.get().getCreated());
                        suscriptor.setUpdated(LocalDateTime.now());
                        LOGGER.info(suscriptor.toString());
                        LOGGER.info(optSuscriptor.toString());

                        return suscriptorRepository.save(suscriptor)
                                .onErrorResume(throwable -> {
                                    LOGGER.error("Error al actualizar el suscriptor: " + suscriptor, throwable);
                                    return Mono.empty();
                                })
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Suscriptor=" + suscriptor +" no actualizado").getMostSpecificCause()));
                    }
                    return Mono.empty();
                });
    }

    public Mono<Suscriptor> deleteSuscriptorById(Integer id) {
        return suscriptorRepository.findById(id)
                .flatMap(suscriptor -> suscriptorRepository.deleteById(suscriptor.getId()).thenReturn(suscriptor))
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al borrar al suscriptor con id" + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscriptor con id: " + id + " no borrado.")
                ));
    }

    public Mono<Void> deleteAllSuscriptores() {
        return suscriptorRepository.deleteAll()
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al borrar los suscriptores", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Suscriptores no borrados").getMostSpecificCause()));
    }

    public Mono<Suscriptor> getSucriptorByIdentificacion(String identificacion) {
        return suscriptorRepository.findByIdentificacion(identificacion)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar el suscriptor con identificacion: " + identificacion, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Suscriptor con identificacion: " + identificacion + " no encontrado").getMostSpecificCause()));
    }

    public Flux<Suscriptor> getSuscriptorByNombre(String nombre) {
        return suscriptorRepository.findByNombre(nombre)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar el suscriptor con nombre: " + nombre, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Suscriptor con nombre: " + nombre + " no encontrado").getMostSpecificCause()));
    }

    protected boolean validarNombre(Suscriptor suscriptor) {
        return !suscriptor.getNombre().isEmpty() &&
                !suscriptor.getIdentificacion().isEmpty() &&
                !suscriptor.getSexo().isEmpty() &&
                !suscriptor.getDireccion().isEmpty() &&
                !suscriptor.getTelefono().isEmpty();
    }
}
