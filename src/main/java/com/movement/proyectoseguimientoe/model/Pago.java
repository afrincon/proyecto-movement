package com.movement.proyectoseguimientoe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Pago {
    @Id
    private Integer id;
    @Column("idSuscriptor")
    private Integer idSuscriptor;
    @Column("fechaPago")
    private String fechaPago;
    @Column("valorPago")
    private Integer valorPago;
    private LocalDateTime created;
}
