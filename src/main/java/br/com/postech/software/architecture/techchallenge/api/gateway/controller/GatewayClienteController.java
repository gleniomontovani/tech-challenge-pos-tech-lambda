package br.com.postech.software.architecture.techchallenge.api.gateway.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ClienteProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.model.Cliente;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.http.Proxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/clientes")
@RequiredArgsConstructor
@Slf4j
public class GatewayClienteController {

	private final Proxy proxy;
	private final ClienteProperties properties;

	@SuppressWarnings("unchecked")
	@GetMapping(produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<List<T>> listarClientes() throws Exception {//@AuthenticationPrincipal Jwt principal
//		proxy.setJwt(principal);
		proxy.setResource(properties.getListall());

		log.debug("Passou aqui no Gateway para listar todos os clientes....");
		return new ResponseEntity<>(proxy.get(ArrayList.class), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "{idCliente}", produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> buscarCliente(
			@PathVariable("idCliente") Long idCliente) throws Exception {//@AuthenticationPrincipal Jwt principal,

//		proxy.setJwt(principal);
		proxy.setResource(properties.getByid());
		String pathParam = Objects.nonNull(idCliente) ? idCliente.toString() : null;
		log.debug("Passou aqui no Gateway para listar o cliente: {} ", pathParam);
		return new ResponseEntity<>((T) proxy.get(Object.class, pathParam), HttpStatus.OK);
	}

//	@SuppressWarnings("unchecked")
//	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<T> salvarCliente(@AuthenticationPrincipal Jwt principal, @RequestBody Cliente cliente)
//			throws Exception {
//		proxy.setJwt(principal);
//		proxy.setResource(properties.getSave());
//
//		return new ResponseEntity<>((T) proxy.post(cliente), HttpStatus.OK);
//	}
//
//	@SuppressWarnings("unchecked")
//	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<T> atualizarCliente(@AuthenticationPrincipal Jwt principal, @PathVariable Long id,
//			@RequestBody Cliente cliente) throws Exception {
//		proxy.setJwt(principal);
//		proxy.setResource(properties.getUpdate());
//		String pathParam = Objects.nonNull(id) ? id.toString() : null;
//
//		return new ResponseEntity<>((T) proxy.put(cliente, pathParam), HttpStatus.OK);
//	}
//
//	@SuppressWarnings("unchecked")
//	@PutMapping(value = "/desativar/{id}", produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<T> desativarCliente(@AuthenticationPrincipal Jwt principal, @PathVariable Long id)
//			throws Exception {
//		proxy.setJwt(principal);
//		proxy.setResource(properties.getDelete());
//		String pathParam = Objects.nonNull(id) ? id.toString() : null;
//
//		return new ResponseEntity<>((T) proxy.delete(pathParam), HttpStatus.OK);
//	}
}