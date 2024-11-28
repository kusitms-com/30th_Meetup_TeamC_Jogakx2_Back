package spring.backend.recommendation.infrastructure.link;

public interface LinkWebClient<T> {

    T search(String query);
}
