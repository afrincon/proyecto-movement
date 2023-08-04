package com.movementapp.seguimientoproyecto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
public class DatosSuscriptor {
    @Id
    private Integer id;
    private Integer idSuscriptor;
    private Date fechaNacimiento;
    private Character sexo;
    private Float peso;
    private Integer altura;
}
