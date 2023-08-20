package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.Suscripcion;
import com.movement.proyectoseguimientoe.service.SuscripcionService;
import com.movement.proyectoseguimientoe.service.SuscriptorService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/suscripcion")
public class SuscripcionController {

    SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @GetMapping("/{id}")
    public Mono<Suscripcion> getSuscriocionById(@PathVariable Integer id) {
        return suscripcionService.getSuscripcionById(id);
    }

    @GetMapping("/")
    public Flux<Suscripcion> getAllSuscipciones() {
        return suscripcionService.getAllSuscripciones();
    }

    @PostMapping("/")
    public Mono<Suscripcion> createSuscripcion(@RequestBody Suscripcion suscripcion) {
        return suscripcionService.createSuscripcion(suscripcion);
    }

    @PutMapping("/{id}")
    public Mono<Suscripcion> updateSuscripcion(Integer id, @RequestBody Suscripcion suscripcion) {
        return suscripcionService.uddateSuscripcion(id, suscripcion);
    }

    @DeleteMapping("/")
    public Mono<Void> deleteAllSuscripciones() {
        return suscripcionService.deleteAllSuscripciones();
    }

    @DeleteMapping("/{id}")
    public Mono<Suscripcion> deleteSuscripcionById(Integer id) {
        return suscripcionService.deleteSuscripcionById(id);
    }

    @GetMapping("/activos")
    public Flux<Suscripcion> getSuscripcionesActivas() {
        return suscripcionService.getSuscionesByEstado(Boolean.TRUE);
    }

    @GetMapping("/inactivos")
    public Flux<Suscripcion> getSuscripcionesInactivas() {
        return suscripcionService.getSuscionesByEstado(Boolean.FALSE);
    }
}
