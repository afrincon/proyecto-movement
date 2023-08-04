package com.movementapp.seguimientoproyecto.repositories;

import com.movementapp.seguimientoproyecto.models.DatosSuscriptor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosSuscriptorRepository extends R2dbcRepository<DatosSuscriptor, Integer> {
}
