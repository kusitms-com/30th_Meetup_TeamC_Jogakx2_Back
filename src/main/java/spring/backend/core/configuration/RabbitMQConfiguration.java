package spring.backend.core.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.backend.core.configuration.property.queue.FinishActivityQueueProperty;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final RabbitProperties rabbitProperties;

    private final FinishActivityQueueProperty finishActivityQueueProperty;

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return connectionFactory;
    }

    @Bean
    public CustomExchange finishActivityExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(finishActivityQueueProperty.getExchange(), "x-delayed-message", true, false, args);
    }

    @Bean
    Queue finishActivityQueue() {
        return new Queue(finishActivityQueueProperty.getQueue(), false);
    }

    @Bean
    Binding bindingFinishActivityQueue(CustomExchange finishActivityExchange) {
        return BindingBuilder.bind(finishActivityQueue())
                .to(finishActivityExchange)
                .with(finishActivityQueueProperty.getRoutingKey())
                .noargs();
    }
}
