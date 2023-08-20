package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.Pago;
import com.movement.proyectoseguimientoe.service.PagoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/")
    public Flux<Pago> getAllPagos() {
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Pago> getPagoById(@PathVariable Integer id) {
        return pagoService.findById(id);
    }

    @PostMapping("/")
    public Mono<Pago> createPago(@RequestBody Pago pago) {
        return pagoService.createPago(pago);
    }

    @DeleteMapping("/{id}")
    public Mono<Pago> deletePagoById(@PathVariable Integer id) {
        return pagoService.deletePagoById(id);
    }
}
