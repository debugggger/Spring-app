package main.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey = "verySecretKey";
    private long validityMs = 1800000;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getValidityMs() {
        return validityMs;
    }

    public void setValidityMs(long validityMs) {
        this.validityMs = validityMs;
    }
}
