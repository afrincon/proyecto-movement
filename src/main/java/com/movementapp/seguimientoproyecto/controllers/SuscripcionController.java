package com.movementapp.seguimientoproyecto.controllers;

import com.movementapp.seguimientoproyecto.models.Suscripcion;
import com.movementapp.seguimientoproyecto.services.SuscripcionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/suscripciones")
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @GetMapping("/")
    public Flux<Suscripcion> findAll(){
        return suscripcionService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Suscripcion> findById(@PathVariable Integer id) {
        return suscripcionService.findById(id);
    }

    @GetMapping("/activas")
    public Flux<Suscripcion> finbByEstadoActivo() {
        return suscripcionService.findByEstado(Boolean.TRUE);
    }

    @PostMapping("/")
    public Mono<Suscripcion> saveSuscripcion(@RequestBody Suscripcion suscripcion) {
        return suscripcionService.save(suscripcion);
    }

    @PutMapping("/{id}")
    public Mono<Suscripcion> updateSuscripcion(@PathVariable Integer id, @RequestBody Suscripcion suscripcion) {
        return suscripcionService.update(id, suscripcion);
    }

    @DeleteMapping("/{id}")
    public Mono<Suscripcion> deleteById(@PathVariable Integer id) {
        return suscripcionService.deleteById(id);
    }

    @DeleteMapping("/")
    public Mono<Void> deleteAll() {
        return suscripcionService.deleteAll();
    }
}
