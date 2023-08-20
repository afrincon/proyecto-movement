package com.movement.proyectoseguimientoe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Suscripcion {
    @Id
    private Integer id;
    @Column("idSuscriptor")
    private Integer idSuscriptor;
    @Column("fechaInicio")
    private String fechaInicio;
    @Column("fechaFinalizacion")
    private String fechaFinalizacion;
    private Boolean estado;
    private LocalDateTime created;
    private LocalDateTime updated;
}
