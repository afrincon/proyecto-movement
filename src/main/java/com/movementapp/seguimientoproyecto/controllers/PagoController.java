package com.movementapp.seguimientoproyecto.controllers;

import com.movementapp.seguimientoproyecto.models.Pago;
import com.movementapp.seguimientoproyecto.services.PagoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/")
    public Flux<Pago> findAll() {
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Pago> findById(@PathVariable Integer id) {
        return pagoService.findById(id);
    }

    @PostMapping("/")
    public Mono<Pago> savePago(@RequestBody Pago pago) {
        return pagoService.save(pago);
    }

    @DeleteMapping("/{id}")
    public Mono<Pago> deleteById(@PathVariable Integer id) {
        return pagoService.deleteById(id);
    }
}
