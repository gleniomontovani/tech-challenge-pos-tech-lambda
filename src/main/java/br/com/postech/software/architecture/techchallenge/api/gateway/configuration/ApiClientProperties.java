package br.com.postech.software.architecture.techchallenge.api.gateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApiClientProperties {

    private String uri;

    @Autowired
    public ApiClientProperties(Environment env) {
        this.uri = env.getProperty("URL_CLIENT_SERVER");
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
