package vk.dentttt.instazoo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.token")
@Getter
@Setter
public class CustomProperties {

    /**
     * Secret string for JWT
    **/
    private String secret;

    /**
     * Sign up url
    **/
    private String signUpUrl;

    /**
     * Expiration time for JWT
     **/
    private long expired;

    /**
     * Authorization header in http request
     **/
    private String header;

    /**
     * Content type in http request
     **/
    private String contentType;

    /**
     * Prefix for JWT body
     **/
    private String prefix;

}
