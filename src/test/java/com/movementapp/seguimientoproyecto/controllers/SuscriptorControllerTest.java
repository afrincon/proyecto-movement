package com.movementapp.seguimientoproyecto.controllers;

import com.movementapp.seguimientoproyecto.models.Suscriptor;
import com.movementapp.seguimientoproyecto.services.SuscriptorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
//TODO: Dejo este comentario porque el problema fue en este import, accidentalmente lo hice mal y entonces por eso reventaba en el when
import static org.mockito.Mockito.when;

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
    void testFindByIdExistoso() throws Exception {
        Integer id = 56;
        Suscriptor suscriptorEsperado = new Suscriptor(id, "Pepito Perez", "123654789","Avenida Siempre Viva 123", "3274027", true);

        when(suscriptorService.findById(any())).thenReturn(Mono.just(suscriptorEsperado));
        Mono<Suscriptor> resultado = suscriptorController.getSuscriptorById(id);
        assertEquals(suscriptorEsperado, resultado.block());
    }

    @Test
    public void testGetSuscriptorByIdNoEncontrado() {
        int id = 100;
        when(suscriptorService.findById(id)).thenReturn(Mono.empty());
        when(suscriptorService.findById(id)).thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Credito not found"));
        assertThrows(ResponseStatusException.class, () -> suscriptorController.getSuscriptorById(id).block());
    }

    @Test
    void testGetAllCreditosExitoso() {
        Suscriptor suscriptorEsperado = new Suscriptor(56, "Pepito Perez", "123654789","Avenida Siempre Viva 123", "3274027", true);
        Suscriptor suscriptorEsperado2 = new Suscriptor(57, "Carlos Perez", "7598963","Avenida Siempre Viva 123", "3274027", true);
        Flux<Suscriptor> expectedSuscriptores = Flux.just(suscriptorEsperado, suscriptorEsperado2);
        when(suscriptorService.findAll()).thenReturn(expectedSuscriptores);
        Flux<Suscriptor> resultado = suscriptorController.getAllSuscriptores();
        resultado.subscribe();
        assertEquals(expectedSuscriptores, resultado);
    }
}