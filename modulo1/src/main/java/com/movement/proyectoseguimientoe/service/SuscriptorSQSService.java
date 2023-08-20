package com.movement.proyectoseguimientoe.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.movement.proyectoseguimientoe.model.Suscriptor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SuscriptorSQSService {

    private final AmazonSQS clienteSQS;

    public SuscriptorSQSService(AmazonSQS clienteSQS) {
        this.clienteSQS = clienteSQS;
    }

    public String createQueue(String queueName) {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        return clienteSQS.createQueue(queueName).getQueueUrl();
    }

    private String getQueueUrl(String queueName){
        return clienteSQS.getQueueUrl(queueName).getQueueUrl();
    }

    public String publishStandardQueueMessage(String queueName, Integer delaySeconds, Suscriptor suscriptor) {
        Map<String, MessageAttributeValue> atributosMensaje = new HashMap<>();

        atributosMensaje.put("id",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(suscriptor.getId()).orElse(-301).toString())
                        .withDataType("Number"));

        atributosMensaje.put("nombre",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(suscriptor.getId()).orElse(-301).toString())
                        .withDataType("String"));

        atributosMensaje.put("identificacion",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(suscriptor.getIdentificacion()).orElse("No identificacion").toString())
                        .withDataType("String"));

        atributosMensaje.put("sexo",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(suscriptor.getSexo()).orElse("No sexo").toString())
                        .withDataType("String"));

        atributosMensaje.put("direccion",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(suscriptor.getDireccion()).orElse("No direccion").toString())
                        .withDataType("String"));

        atributosMensaje.put("telefono",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(suscriptor.getTelefono()).orElse("No telefono").toString())
                        .withDataType("String"));

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl(queueName))
                .withMessageBody(suscriptor.getNombre() + " - " + suscriptor.getIdentificacion())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(atributosMensaje);

        return clienteSQS.sendMessage(sendMessageRequest).getMessageId();
    }

    public void publishStandardQueueMessage(String queueName, Integer delaySeconds, List<Suscriptor> suscriptores){
        for (Suscriptor suscriptor : suscriptores){
            publishStandardQueueMessage(queueName, delaySeconds, suscriptor);
        }
    }

    private List<Message> receiveMessagesFromQueue(String queueName, Integer maxNumberMessages, Integer waitTimeSeconds) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(this.getQueueUrl(queueName))
                .withMaxNumberOfMessages(maxNumberMessages)
                .withMessageAttributeNames(List.of("All"))
                .withWaitTimeSeconds(waitTimeSeconds);
        return clienteSQS.receiveMessage(receiveMessageRequest).getMessages();
    }

}
