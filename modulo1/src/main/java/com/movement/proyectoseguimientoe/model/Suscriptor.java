package com.movement.proyectoseguimientoe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Suscriptor {
    @Id
    private Integer id;
    private String nombre;
    private String identificacion;
    private String sexo;
    private String direccion;
    private String telefono;
    private Boolean estado;
    private LocalDateTime created;
    private LocalDateTime updated;
}
