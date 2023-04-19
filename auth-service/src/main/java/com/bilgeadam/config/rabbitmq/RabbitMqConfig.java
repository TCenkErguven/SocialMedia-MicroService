package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange-auth}")
    String exchange;
    @Value("${rabbitmq.registerKey}")
    String registerBindingKey;
    @Value("${rabbitmq.queueRegister}")
    String queueNameRegister;

    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue registerQueue(){
        return new Queue(queueNameRegister);
    }

    public Binding bindingRegister(final Queue queue,DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(registerBindingKey);
    }

}
