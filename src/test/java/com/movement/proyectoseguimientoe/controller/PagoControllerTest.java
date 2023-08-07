package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.Pago;
import com.movement.proyectoseguimientoe.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagoControllerTest {

    @Mock
    private PagoService pagoService;

    @InjectMocks
    private PagoController pagoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Consultar pago con id y obtener resultado exitoso")
    void testGetPagoByIdExitoso() throws Exception {
        int id = 25;
        Pago pagoEsperado = new Pago(id, 1, "08/08/2023",
                250000, LocalDateTime.now());
        when(pagoService.findById(any())).thenReturn(Mono.just(pagoEsperado));
        Mono<Pago> resultado = pagoController.getPagoById(id);
        assertEquals(pagoEsperado, resultado.block());
    }

    @Test
    @DisplayName("Consulta pago con id no existente")
    void testGetPagoByIdNoEncontrado() {
        int id = 25;
        when(pagoService.findById(id)).thenReturn(Mono.empty());
        when(pagoService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
        assertThrows(ResponseStatusException.class, () -> pagoController.getPagoById(id).block());
    }

    @Test
    @DisplayName("Crear pago exitosamente")
    void testCreatePagoExitoso() {
        Pago pagoEsperado = new Pago(25, 1, "08/08/2023",
                250000, LocalDateTime.now());
        when(pagoService.createPago(any(Pago.class))).thenReturn(Mono.just(pagoEsperado));
        Mono<Pago> resultado = pagoController.createPago(pagoEsperado);

        StepVerifier.create(resultado)
                .expectNext(pagoEsperado)
                .verifyComplete();
    }

    @Test
    @DisplayName("Crear pago no exitoso")
    void testCreatePagoNoExitoso() {
        Pago pagoEsperado = new Pago(25, 1, "08/08/2023",
                250000, LocalDateTime.now());
        when(pagoService.createPago(any(Pago.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "pago no guardado")));

        StepVerifier.create(pagoController.createPago(pagoEsperado))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("Borrar pago exitoso")
    void testDeletePagoByIdExitoso() {
        int id = 25;
        Pago pagoABorrar = new Pago(id, 1, "08/08/2023",
                250000, LocalDateTime.now());

        when(pagoService.deletePagoById(any())).thenReturn(Mono.just(pagoABorrar));
        Mono <Pago> resultado = pagoController.deletePagoById(id);
        assertEquals(pagoABorrar, resultado.block());
    }

    @Test
    @DisplayName("Borrar pago Fallido")
    void testDeletePagoByIdFallido() {
        int id = 100;
        when(pagoService.deletePagoById(id)).thenReturn(Mono.empty());
        when(pagoService.deletePagoById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "pago no borrado"));
        assertThrows(ResponseStatusException.class, () -> pagoController.deletePagoById(id).block());
    }
}