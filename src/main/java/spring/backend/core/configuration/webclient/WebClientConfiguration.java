package spring.backend.core.configuration.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${clova.api.api-key}")
    private String apiKey;

    @Value("${clova.api.api-gateway-key}")
    private String apiGatewayKey;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.defaultHeaders(
                httpHeaders -> {
                    httpHeaders.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
                    httpHeaders.set("X-NCP-APIGW-API-KEY", apiGatewayKey);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                }
        ).build();
    }
}
