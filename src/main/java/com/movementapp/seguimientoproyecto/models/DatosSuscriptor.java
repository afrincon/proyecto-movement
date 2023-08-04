package com.movementapp.seguimientoproyecto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@AllArgsConstructor
@Table("datos_suscriptor")
public class DatosSuscriptor {
    @Id
    private Integer id;
    private Integer idSuscriptor;
    private Date fechaNacimiento;
    private Character sexo;
    private Float peso;
    private Integer altura;
}
