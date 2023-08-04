package com.movementapp.seguimientoproyecto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
public class Suscripcion {
    @Id
    private Integer id;
    private Integer idSuscriptor;
    private Date fechaInicio;
    private Date fechaFinalizacion;
    private Boolean estado;
}
