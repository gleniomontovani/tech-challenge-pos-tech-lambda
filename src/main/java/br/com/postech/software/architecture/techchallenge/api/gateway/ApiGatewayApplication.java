package br.com.postech.software.architecture.techchallenge.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ApiGatewayClientProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ClienteProperties;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties({ApiGatewayClientProperties.class, ClienteProperties.class})
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
