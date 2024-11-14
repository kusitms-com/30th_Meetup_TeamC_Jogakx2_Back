package spring.backend.core.configuration.property.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import spring.backend.core.configuration.property.oauth.shared.BaseOAuthProperty;

@Component
@Getter
@Setter
@ConfigurationProperties("oauth2.google")
public class GoogleOAuthProperty extends BaseOAuthProperty {
}
