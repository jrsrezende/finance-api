package br.com.jrsr.financeapi.infrastructure.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = "finance.queue")
    public void receive(@Payload String message) {
        System.out.println("Received message: " + message);
    }
}
