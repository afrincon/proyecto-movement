package com.movementapp.seguimientoproyecto.repositories;

import com.movementapp.seguimientoproyecto.models.Pago;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PagoRepository extends R2dbcRepository<Pago, Integer> {
}
