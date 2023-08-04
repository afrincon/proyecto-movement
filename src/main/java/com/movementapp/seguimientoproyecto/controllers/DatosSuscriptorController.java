package com.movementapp.seguimientoproyecto.controllers;

import com.movementapp.seguimientoproyecto.models.DatosSuscriptor;
import com.movementapp.seguimientoproyecto.services.DatosSuscriptorService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/datos-suscriptor")
public class DatosSuscriptorController {

    private final DatosSuscriptorService datosSuscriptorService;

    public DatosSuscriptorController(DatosSuscriptorService datosSuscriptorService) {
        this.datosSuscriptorService = datosSuscriptorService;
    }

    @GetMapping("/{id}")
    public Mono<DatosSuscriptor> findById(@PathVariable Integer id) {
        return datosSuscriptorService.findById(id);
    }

    @PostMapping("/")
    public Mono<DatosSuscriptor> saveDatos(@RequestBody DatosSuscriptor datosSuscriptor) {
        return datosSuscriptorService.save(datosSuscriptor);
    }

    @DeleteMapping("/{id}")
    public Mono<DatosSuscriptor> deleteById(@PathVariable Integer id) {
        return datosSuscriptorService.deleteById(id);
    }

}
