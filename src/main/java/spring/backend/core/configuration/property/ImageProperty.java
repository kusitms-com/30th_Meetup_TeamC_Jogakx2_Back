package spring.backend.core.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("image")
public class ImageProperty {

    private String selfDevelopmentImageUrl;
    private String healthImageUrl;
    private String cultureArtImageUrl;
    private String entertainmentImageUrl;
    private String relaxationImageUrl;
    private String socialImageUrl;

    private String transparent30SelfDevelopmentImageUrl;
    private String transparent30HealthImageUrl;
    private String transparent30CultureArtImageUrl;
    private String transparent30EntertainmentImageUrl;
    private String transparent30RelaxationImageUrl;
    private String transparent30SocialImageUrl;
}
