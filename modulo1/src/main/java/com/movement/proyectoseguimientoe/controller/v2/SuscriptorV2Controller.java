package com.movement.proyectoseguimientoe.controller.v2;


import com.movement.proyectoseguimientoe.model.Suscriptor;
import com.movement.proyectoseguimientoe.service.SuscriptorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/suscriptor")
public class SuscriptorV2Controller {

    SuscriptorService suscriptorService;

    public SuscriptorV2Controller(SuscriptorService suscriptorService ) {
        this.suscriptorService = suscriptorService;

    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public Flux<Suscriptor> getAllSuscriptor() {
        return suscriptorService.getAllSuscriptors();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Mono<Suscriptor> getSuscriptorById(@PathVariable Integer id) {
        return suscriptorService.getSuscriptorById(id);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Suscriptor> createSuscriptor(@RequestBody Suscriptor suscriptor){
        return suscriptorService.saveSuscriptor(suscriptor);
    }
}
