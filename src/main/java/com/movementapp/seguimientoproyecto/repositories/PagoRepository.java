package com.movementapp.seguimientoproyecto.repositories;

import com.movementapp.seguimientoproyecto.models.Pago;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends R2dbcRepository<Pago, Integer> {
}
