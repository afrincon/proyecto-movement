package com.movement.proyectoseguimientoe.service;

import com.movement.proyectoseguimientoe.model.Pago;
import com.movement.proyectoseguimientoe.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PagoService {

    private final Logger LOGGER = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Flux<Pago> findAll() {
        return pagoRepository.findAll()
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar todos los pagos", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ningun pago encontrado").getMostSpecificCause()));
    }

    public Mono<Pago> findById(Integer id) {
        return pagoRepository.findById(id)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar el pago con id: " + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "pago con id: " + id + " no encontrado").getMostSpecificCause()));
    }

    public Mono<Pago> createPago(Pago pago)  {
        if(validateValorPago(pago)) {
            pago.setCreated(LocalDateTime.now());
            return pagoRepository.save(pago)
                    .onErrorResume(throwable -> {
                        LOGGER.error("Error al guardar al pago" +  pago, throwable);
                        return Mono.empty();
                    })
                    .switchIfEmpty(Mono.error(
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no guardado").getMostSpecificCause()
                    ));
        }
        return Mono.empty();
    }

    public Mono<Pago> deletePagoById(Integer id) {
        return pagoRepository.findById(id)
                .flatMap(pago -> pagoRepository.deleteById(pago.getId()).thenReturn(pago))
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al borrar al pago con id" + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "pago con id: " + id + " no borrado.")
                ));
    }

    protected Boolean validateValorPago(Pago pago) {
        return pago.getValorPago() >= 0;
    }
}
