package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.Suscriptor;
import com.movement.proyectoseguimientoe.service.SuscriptorService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/suscriptor")
public class SuscriptorController {

    SuscriptorService suscriptorService;

    public SuscriptorController(SuscriptorService suscriptorService) {
        this.suscriptorService = suscriptorService;
    }

    @GetMapping("/")
    public Flux<Suscriptor> getAllSuscriptor() {
        return suscriptorService.getAllSuscriptors();
    }

    @GetMapping("/{id}")
    public Mono<Suscriptor> getSuscriptorById(@PathVariable Integer id) {
        return suscriptorService.getSuscriptorById(id);
    }

    @PostMapping("/")
    public Mono<Suscriptor> createSuscriptor(@RequestBody Suscriptor suscriptor){
        return suscriptorService.saveSuscriptor(suscriptor);
    }

    @PutMapping("/{id}")
    public Mono<Suscriptor> updateSuscriptor(@PathVariable Integer id, @RequestBody Suscriptor suscriptor) {
        return suscriptorService.updateSuscriptor(id, suscriptor);
    }

    @DeleteMapping("/{id}")
    public Mono<Suscriptor> deleteSuscriptorbyId(@PathVariable Integer id) {
        return suscriptorService.deleteSuscriptorById(id);
    }

    @DeleteMapping("/")
    public Mono<Void> deleteAllSuscriptores() {
        return suscriptorService.deleteAllSuscriptores();
    }

    @GetMapping("/identificacion/{identificacion}")
    public Mono<Suscriptor> getSuscriptorByIdentificacion(@PathVariable String identificacion) {
        return suscriptorService.getSucriptorByIdentificacion(identificacion);
    }

    @GetMapping("/nombre/{nombre}")
    public Flux<Suscriptor> getSusciptorByNombre(@PathVariable String nombre) {
        return suscriptorService.getSuscriptorByNombre(nombre);
    }
}
