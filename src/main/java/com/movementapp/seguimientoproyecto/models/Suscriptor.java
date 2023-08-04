package com.movementapp.seguimientoproyecto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Suscriptor {
    @Id
    private Integer id;
    private String nombre;
    private String identificacion;
    private String direccion;
    private String telefono;
    private Boolean estado;
}
