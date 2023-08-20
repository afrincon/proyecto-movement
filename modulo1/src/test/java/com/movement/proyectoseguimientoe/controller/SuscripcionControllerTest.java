package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.Suscripcion;
import com.movement.proyectoseguimientoe.model.Suscriptor;
import com.movement.proyectoseguimientoe.service.SuscripcionService;
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
class SuscripcionControllerTest {
    @Mock
    private SuscripcionService suscripcionService;

    @InjectMocks
    private SuscripcionController suscripcionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Consulta suscripcion con id y obtener resultado exitoso")
    void testGetSuscriptorByIdExitoso() throws Exception {
        int id = 25;
        Suscripcion suscripcionEsperada = new Suscripcion(id, 1, "2018-01-01", "2018-02-02", false, LocalDateTime.now(), LocalDateTime.now());
        when(suscripcionService.getSuscripcionById(any())).thenReturn(Mono.just(suscripcionEsperada));
        Mono<Suscripcion> resultado = suscripcionController.getSuscriocionById(id);
        assertEquals(suscripcionEsperada, resultado.block());
    }

    @Test
    @DisplayName("Consulta suscripcion con id no existente")
    void testGetSuscriptorIdNoEncontrado() {
        int id = 100;
        when(suscripcionService.getSuscripcionById(id)).thenReturn(Mono.empty());
        when(suscripcionService.getSuscripcionById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscriptor not found"));
        assertThrows(ResponseStatusException.class, () -> suscripcionController.getSuscriocionById(id).block());
    }

    @Test
    @DisplayName("Crear suscripcion exitosamente")
    void testCreateSuscriptorExitoso() {
        Suscripcion suscripcionEsperada = new Suscripcion(25, 1, "2018-01-01", "2018-02-02", false, LocalDateTime.now(), LocalDateTime.now());
        when(suscripcionService.createSuscripcion(any(Suscripcion.class))).thenReturn(Mono.just(suscripcionEsperada));
        Mono<Suscripcion> resultado = suscripcionController.createSuscripcion(suscripcionEsperada);

        StepVerifier.create(resultado)
                .expectNext(suscripcionEsperada)
                .verifyComplete();
    }

    @Test
    @DisplayName("Crear suscripcion Fallido")
    void testCreateSuscriptorError() {
        Suscripcion suscripcionEsperada = new Suscripcion(25, 1, "2018-01-01", "2018-02-02", false, LocalDateTime.now(), LocalDateTime.now());
        when(suscripcionService.createSuscripcion(any(Suscripcion.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscriptor no creado")));

        StepVerifier.create(suscripcionController.createSuscripcion(suscripcionEsperada))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("Borrar suscripcion Exitoso")
    void testDeleteSuscriptorByIdExitoso() {
        int id = 100;
        Suscripcion suscripcionABorrar = new Suscripcion(id, 1, "2018-01-01", "2018-02-02", false, LocalDateTime.now(), LocalDateTime.now());
        when(suscripcionService.deleteSuscripcionById(any())).thenReturn(Mono.just(suscripcionABorrar));
        Mono<Suscripcion> resultado = suscripcionController.deleteSuscripcionById(id);
        assertEquals(suscripcionABorrar, resultado.block());
    }

    @Test
    @DisplayName("Borrar suscripcion Fallido")
    void testDeleteSuscriptorByIdFallido() {
        int id = 100;
        when(suscripcionService.deleteSuscripcionById(id)).thenReturn(Mono.empty());
        when(suscripcionService.deleteSuscripcionById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscriptor no borrado"));
        assertThrows(ResponseStatusException.class, () -> suscripcionController.deleteSuscripcionById(id).block());
    }

    @Test
    @DisplayName("Borrar todos las suscripciones Exitoso")
    void testDeleteAllSuscriptoresExitoso() {
        when(suscripcionService.deleteAllSuscripciones()).thenReturn(Mono.empty());
        Mono<Void> resultado = suscripcionController.deleteAllSuscripciones();
        resultado.subscribe();
        assertEquals(Mono.empty(), resultado);
    }
}