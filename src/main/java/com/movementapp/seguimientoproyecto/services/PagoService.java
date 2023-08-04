package com.movementapp.seguimientoproyecto.services;

import com.movementapp.seguimientoproyecto.models.Pago;
import com.movementapp.seguimientoproyecto.repositories.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PagoService {

    private final Logger logger = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Flux<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public Mono<Pago> findById(Integer id) {
        return pagoRepository.findById(id);
    }

    public Mono<Pago> save(Pago pago) {
        return pagoRepository.save(pago);
    }

    public Mono<Pago> deleteById(Integer id) {
        return pagoRepository.findById(id)
                .flatMap(pago -> pagoRepository.deleteById(pago.getId()).thenReturn(pago));
    }
}
