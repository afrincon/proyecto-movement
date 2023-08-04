package com.movementapp.seguimientoproyecto.controllers;

import com.movementapp.seguimientoproyecto.dto.DTOSusciptorNombreEstado;
import com.movementapp.seguimientoproyecto.models.Suscriptor;
import com.movementapp.seguimientoproyecto.services.SuscriptorService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/suscriptores")
public class SuscriptorController {

    private final SuscriptorService suscriptorService;

    public SuscriptorController(SuscriptorService suscriptorService) {
        this.suscriptorService = suscriptorService;
    }

    @GetMapping("/")
    public Flux<Suscriptor> getAllSuscriptores() {
        return suscriptorService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Suscriptor> getSuscriptorById(@PathVariable Integer id) {
        return suscriptorService.findById(id);
    }

    @GetMapping("/activos")
    public Flux<Suscriptor> findByEstadoActivo(){
        return suscriptorService.findByEstado(Boolean.TRUE);
    }

    @GetMapping("/inactivos")
    public Flux<Suscriptor> findByEstadoInactivo() {
        return suscriptorService.findByEstado(Boolean.FALSE);
    }

    @GetMapping("/por-nombre-por-estado")
    public Flux<Suscriptor> findByNombreByEstado(@RequestBody DTOSusciptorNombreEstado dtoSusciptorNombreEstado) {
        return suscriptorService
                .findByNombreByEstado(dtoSusciptorNombreEstado.Nombre(), dtoSusciptorNombreEstado.estado());
    }

    @PostMapping("/")
    public Mono<Suscriptor> saveSuscriptor(@RequestBody Suscriptor suscriptor) {
        return suscriptorService.save(suscriptor);
    }

    @PutMapping("/")
    public Mono<Suscriptor> updateSuscriptor(@PathVariable Integer id, @RequestBody Suscriptor suscriptor) {
        return suscriptorService.update(id, suscriptor);
    }

    @DeleteMapping("/{id}")
    public Mono<Suscriptor> deleteById(@PathVariable Integer id) {
        return suscriptorService.deleteById(id);
    }

    @DeleteMapping("/")
    public Mono<Void> deleteAll() {
        return suscriptorService.deleteAll();
    }

}
