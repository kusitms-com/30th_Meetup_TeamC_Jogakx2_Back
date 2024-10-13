package spring.backend.core.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import spring.backend.core.configuration.property.shared.BaseOAuthProperty;

@Component
@Getter
@Setter
@ConfigurationProperties("oauth2.naver")
public class NaverOAuthProperty extends BaseOAuthProperty {
}
