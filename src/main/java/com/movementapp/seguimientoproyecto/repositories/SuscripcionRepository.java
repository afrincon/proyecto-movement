package com.movementapp.seguimientoproyecto.repositories;

import com.movementapp.seguimientoproyecto.models.Suscripcion;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SuscripcionRepository extends R2dbcRepository<Suscripcion, Integer> {

    Flux<Suscripcion> findByEstado(Boolean estado);
}
