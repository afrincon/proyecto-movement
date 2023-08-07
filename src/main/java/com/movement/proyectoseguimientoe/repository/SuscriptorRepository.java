package com.movement.proyectoseguimientoe.repository;

import com.movement.proyectoseguimientoe.model.Suscriptor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SuscriptorRepository extends R2dbcRepository<Suscriptor, Integer> {
    Flux<Suscriptor> findByNombre(String nombre);

    Mono<Suscriptor> findByIdentificacion(String identificacion);
}
