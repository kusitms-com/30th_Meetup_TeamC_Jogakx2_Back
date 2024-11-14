package spring.backend.core.infrastructure.queue;

public interface MessageConsumer<T> {

    void consumeMessage(T message);
}
