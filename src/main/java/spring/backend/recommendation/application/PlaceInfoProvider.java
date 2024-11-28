package spring.backend.recommendation.application;

public interface PlaceInfoProvider<T> {
    T search(String query);
}
