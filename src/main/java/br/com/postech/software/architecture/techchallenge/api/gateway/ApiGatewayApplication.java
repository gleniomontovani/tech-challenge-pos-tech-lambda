package br.com.postech.software.architecture.techchallenge.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ApiClientProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ClienteProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.PagamentoProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.PedidoProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ProdutoProperties;

@SpringBootApplication
//@EnableWebSecurity
@EnableConfigurationProperties({ ClienteProperties.class, ProdutoProperties.class,
		PedidoProperties.class, PagamentoProperties.class })
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
