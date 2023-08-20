package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.DatosSuscriptor;
import com.movement.proyectoseguimientoe.service.DatosSuscriptorService;
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
class DatosSuscriptorControllerTest {

    @Mock
    DatosSuscriptorService datosSuscriptorService;

    @InjectMocks
    DatosSuscriptorController datosSuscriptorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Consultar datos del suscriptor con id y obtener resultado exitoso")
    void testGetPagoByIdExitoso() throws Exception {
        int id = 25;
        DatosSuscriptor datosEsperados = new DatosSuscriptor(id, 1, "2020-01-01", 85.9F, 171, LocalDateTime.now());
        when(datosSuscriptorService.getDatosSuscripdorByID(any())).thenReturn(Mono.just(datosEsperados));
        Mono<DatosSuscriptor> resultado = datosSuscriptorController.getDatosSuscriptorById(id);
        assertEquals(datosEsperados, resultado.block());
    }

    @Test
    @DisplayName("Consulta datos del suscriptor con id no existente")
    void testGetPagoByIdNoEncontrado() {
        int id = 25;
        when(datosSuscriptorService.getDatosSuscripdorByID(id)).thenReturn(Mono.empty());
        when(datosSuscriptorService.getDatosSuscripdorByID(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "datos no encontrados"));
        assertThrows(ResponseStatusException.class, () -> datosSuscriptorController.getDatosSuscriptorById(id).block());
    }

    @Test
    @DisplayName("Crear datos del suscriptor exitosamente")
    void testCreatePagoExitoso() {
        int id = 25;
        DatosSuscriptor datosEsperados = new DatosSuscriptor(id, 1, "2020-01-01", 85.9F, 171, LocalDateTime.now());
        when(datosSuscriptorService.createDatosSuscriptor(any(DatosSuscriptor.class))).thenReturn(Mono.just(datosEsperados));
        Mono<DatosSuscriptor> resultado = datosSuscriptorController.createDatosSuscriptor(datosEsperados);

        StepVerifier.create(resultado)
                .expectNext(datosEsperados)
                .verifyComplete();
    }

    @Test
    @DisplayName("Crear datos del suscriptor no exitoso")
    void testCreatePagoNoExitoso() {
        int id = 25;
        DatosSuscriptor datosEsperados = new DatosSuscriptor(id, 1, "2020-01-01", 85.9F, 171, LocalDateTime.now());
        when(datosSuscriptorService.createDatosSuscriptor(any(DatosSuscriptor.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "datos no guardados")));

        StepVerifier.create(datosSuscriptorController.createDatosSuscriptor(datosEsperados))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("Borrar datos del suscriptor exitoso")
    void testDeletePagoByIdExitoso() {
        int id = 25;
        DatosSuscriptor datosaBorrar = new DatosSuscriptor(id, 1, "2020-01-01", 85.9F, 171, LocalDateTime.now());

        when(datosSuscriptorService.deleteDatosSuscripdorById(any())).thenReturn(Mono.just(datosaBorrar));
        Mono <DatosSuscriptor> resultado = datosSuscriptorController.deleteDatosSuscriptorById(id);
        assertEquals(datosaBorrar, resultado.block());
    }

    @Test
    @DisplayName("Borrar datos del suscriptor Fallido")
    void testDeletePagoByIdFallido() {
        int id = 100;
        when(datosSuscriptorService.deleteDatosSuscripdorById(id)).thenReturn(Mono.empty());
        when(datosSuscriptorService.deleteDatosSuscripdorById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "pago no borrado"));
        assertThrows(ResponseStatusException.class, () -> datosSuscriptorController.deleteDatosSuscriptorById(id).block());
    }

}