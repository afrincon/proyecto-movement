package com.movementapp.seguimientoproyecto.repositories;

import com.movementapp.seguimientoproyecto.models.Suscripcion;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface SuscripcionRepository extends R2dbcRepository<Suscripcion, Integer> {

    Flux<Suscripcion> findByEstado(Boolean estado);
}
