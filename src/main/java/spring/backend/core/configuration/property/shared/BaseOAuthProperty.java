package spring.backend.core.configuration.property.shared;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BaseOAuthProperty {

    protected String clientId;

    protected String clientSecret;

    protected String redirectUri;

    protected Set<String> scope;

    protected String tokenUri;

    protected String resourceUri;

    protected String authUri;
}
