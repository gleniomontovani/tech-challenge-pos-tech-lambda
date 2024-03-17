package br.com.postech.software.architecture.techchallenge.api.gateway.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ClienteProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.http.Proxy;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/clientes")
@RequiredArgsConstructor
public class GatewayClienteController {

	private final Proxy proxy;
	private final ClienteProperties properties;

	@GetMapping(produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public List<Object> listarClientes(@AuthenticationPrincipal Jwt principal) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getListall());

		return proxy.get(new ArrayList<>());
	}

	@GetMapping(path = "{idCliente}", produces = MediaType.APPLICATION_JSON)
	public Object buscarCliente(@AuthenticationPrincipal Jwt principal, @PathVariable("idCliente") Long idCliente)
			throws Exception {

		proxy.setJwt(principal);
		proxy.setResource(properties.getByid());
		String pathParam = Objects.nonNull(idCliente) ? idCliente.toString() : null;

		return proxy.get(Object.class, pathParam);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public Object salvarCliente() {
		return "Autenticado...";
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public Object atualizarCliente() {
		return "Autenticado...";
	}

}
