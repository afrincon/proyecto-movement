package com.movement.proyectoseguimientoe.repository;

import com.movement.proyectoseguimientoe.model.Suscripcion;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SuscripcionRepository extends R2dbcRepository<Suscripcion, Integer> {

    Flux<Suscripcion> findByEstado(Boolean estado);
}
