package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.Suscriptor;
import com.movement.proyectoseguimientoe.service.SuscriptorService;
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
class SuscriptorControllerTest {

    @Mock
    private SuscriptorService suscriptorService;

    @InjectMocks
    private SuscriptorController suscriptorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Consulta suscriptor con id y obtener resultado exitoso")
    void testGetSuscriptorByIdExitoso() throws Exception {
        int id = 25;
        Suscriptor suscriptorEsperado = new Suscriptor(id, "Andres", "14256957", "M",
                "avenida siempre viva 123", "5240365", true, LocalDateTime.now(), LocalDateTime.now());
        when(suscriptorService.getSuscriptorById(any())).thenReturn(Mono.just(suscriptorEsperado));
        Mono<Suscriptor> resultado = suscriptorController.getSuscriptorById(id);
        assertEquals(suscriptorEsperado, resultado.block());
    }

    @Test
    @DisplayName("Consulta suscriptor con id no existente")
    void testGetSuscriptorIdNoEncontrado() {
        int id = 100;
        when(suscriptorService.getSuscriptorById(id)).thenReturn(Mono.empty());
        when(suscriptorService.getSuscriptorById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscriptor not found"));
        assertThrows(ResponseStatusException.class, () -> suscriptorController.getSuscriptorById(id).block());
    }

    @Test
    @DisplayName("Crear suscriptor exitosamente")
    void testCreateSuscriptorExitoso() {
        Suscriptor suscriptorEsperado = new Suscriptor(25, "Andres", "14256957", "M",
                "avenida siempre viva 123", "5240365", true, LocalDateTime.now(), LocalDateTime.now());
        when(suscriptorService.saveSuscriptor(any(Suscriptor.class))).thenReturn(Mono.just(suscriptorEsperado));
        Mono<Suscriptor> resultado = suscriptorController.createSuscriptor(suscriptorEsperado);

        StepVerifier.create(resultado)
                .expectNext(suscriptorEsperado)
                .verifyComplete();
    }

    @Test
    @DisplayName("Crear suscriptor Fallido")
    void testCreateSuscriptorError() {
        Suscriptor suscriptorEsperado = new Suscriptor(25, "Andres", "14256957", "M",
                "avenida siempre viva 123", "5240365", true, LocalDateTime.now(), LocalDateTime.now());
        when(suscriptorService.saveSuscriptor(any(Suscriptor.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscriptor no creado")));

        StepVerifier.create(suscriptorController.createSuscriptor(suscriptorEsperado))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("Borrar suscriptor Exitoso")
    void testDeleteSuscriptorByIdExitoso() {
        int id = 100;
        Suscriptor suscriptorABorrar = new Suscriptor(25, "Andres", "14256957", "M",
                "avenida siempre viva 123", "5240365", true, LocalDateTime.now(), LocalDateTime.now());
        when(suscriptorService.deleteSuscriptorById(any())).thenReturn(Mono.just(suscriptorABorrar));
        Mono<Suscriptor> resultado = suscriptorController.deleteSuscriptorbyId(id);
        assertEquals(suscriptorABorrar, resultado.block());
    }

    @Test
    @DisplayName("Borrar suscriptor Fallido")
    void testDeleteSuscriptorByIdFallido() {
        int id = 100;
        when(suscriptorService.deleteSuscriptorById(id)).thenReturn(Mono.empty());
        when(suscriptorService.deleteSuscriptorById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscriptor no borrado"));
        assertThrows(ResponseStatusException.class, () -> suscriptorController.deleteSuscriptorbyId(id).block());
    }

    @Test
    @DisplayName("Borrar todos los suscriptore Fallido")
    void testDeleteAllSuscriptoresExitoso() {
        when(suscriptorService.deleteAllSuscriptores()).thenReturn(Mono.empty());
        Mono<Void> resultado = suscriptorController.deleteAllSuscriptores();
        resultado.subscribe();
        assertEquals(Mono.empty(), resultado);
    }

}