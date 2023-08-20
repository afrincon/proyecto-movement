package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.DatosSuscriptor;
import com.movement.proyectoseguimientoe.service.DatosSuscriptorService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/datos-suscriptor")
public class DatosSuscriptorController {

    DatosSuscriptorService datosSuscriptorService;

    public DatosSuscriptorController(DatosSuscriptorService datosSuscriptorService) {
        this.datosSuscriptorService = datosSuscriptorService;
    }

    @GetMapping("/{id}")
    public Mono<DatosSuscriptor> getDatosSuscriptorById(@PathVariable Integer id) {
        return datosSuscriptorService.getDatosSuscripdorByID(id);
    }

    @PostMapping("/")
    public Mono<DatosSuscriptor> createDatosSuscriptor(@RequestBody DatosSuscriptor datosSuscriptor) {
        return datosSuscriptorService.createDatosSuscriptor(datosSuscriptor);
    }

    @DeleteMapping("/{id}")
    public Mono<DatosSuscriptor> deleteDatosSuscriptorById(@PathVariable Integer id) {
        return datosSuscriptorService.deleteDatosSuscripdorById(id);
    }
}
