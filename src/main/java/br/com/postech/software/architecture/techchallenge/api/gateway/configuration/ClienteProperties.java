package br.com.postech.software.architecture.techchallenge.api.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "api.gateway.server.cliente.path")
public class ClienteProperties {

	private String listall;
	private String byid;
	private String save;
	private String update;
	private String delete;
}
