package com.movement.proyectoseguimientoe.repository;

import com.movement.proyectoseguimientoe.model.Pago;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends R2dbcRepository<Pago, Integer> {
}
