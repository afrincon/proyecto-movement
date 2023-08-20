package com.movement.proyectoseguimientoe.controller;

import com.movement.proyectoseguimientoe.model.Suscriptor;
import com.movement.proyectoseguimientoe.service.SuscriptorKafkaConsumerService;
import com.movement.proyectoseguimientoe.service.SuscriptorService;
import com.movement.proyectoseguimientoe.service.SuscriptorSQSService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/suscriptor")
public class SuscriptorController {

    SuscriptorService suscriptorService;

    SuscriptorKafkaConsumerService suscriptorKafkaConsumerService;

    SuscriptorSQSService suscriptorSqsService;

    public SuscriptorController(SuscriptorService suscriptorService,
                                SuscriptorKafkaConsumerService suscriptorKafkaConsumerService,
                                SuscriptorSQSService suscriptorSqsService) {
        this.suscriptorService = suscriptorService;
        this.suscriptorKafkaConsumerService = suscriptorKafkaConsumerService;
        this.suscriptorSqsService = suscriptorSqsService;
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

    @GetMapping("/topico-kafka/{topico}")
    public Mono<String> getSuscriptorFromTopicoKafka(@PathVariable String topico) {
        return Mono.just(suscriptorKafkaConsumerService.obtenerSuscripcion(topico));
    }

    @PostMapping("/aws/createQueue")
    public Mono<String> postCreateQueue(@RequestBody Map<String, Object> requestBody){
        return Mono.just(suscriptorSqsService.createQueue((String) requestBody.get("queueName")));
    }

    @PostMapping("/aws/postMessageQueue/{queueName}")
    public Mono<String> postMessageQueue(@RequestBody Suscriptor suscriptor, @PathVariable String queueName){
        return Mono.just(suscriptorSqsService.publishStandardQueueMessage(
                queueName,
                2,
                suscriptor));
    }
}
