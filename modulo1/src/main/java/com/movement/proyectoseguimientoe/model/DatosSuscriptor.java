package com.movement.proyectoseguimientoe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DatosSuscriptor {
    @Id
    private Integer id;
    @Column("idSuscriptor")
    private Integer idSuscriptor;
    @Column("fechaNacimiento")
    private String fechaNacimiento;
    private Float peso;
    private Integer altura;
    private LocalDateTime created;
}
