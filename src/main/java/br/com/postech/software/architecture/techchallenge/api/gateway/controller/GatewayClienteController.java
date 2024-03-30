package br.com.postech.software.architecture.techchallenge.api.gateway.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.software.architecture.techchallenge.api.gateway.model.Cliente;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.integracao.client.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/clientes")
@RequiredArgsConstructor
@Slf4j
public class GatewayClienteController {

	private final ClienteService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<List<T>> listarClientes(@AuthenticationPrincipal Jwt principal) throws Exception {
		log.debug("Passou aqui no Gateway para listar todos os clientes....");
		return new ResponseEntity<>(service.listarClientes(principal), HttpStatus.OK);
	}

	@GetMapping(path = "{idCliente}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> buscarCliente(@AuthenticationPrincipal Jwt principal,
			@PathVariable("idCliente") Long idCliente) throws Exception {

		return new ResponseEntity<>(service.buscarCliente(principal, idCliente), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> salvarCliente(@AuthenticationPrincipal Jwt principal, @RequestBody Cliente cliente)
			throws Exception {

		return new ResponseEntity<>(service.salvarCliente(principal, cliente), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> atualizarCliente(@AuthenticationPrincipal Jwt principal, @PathVariable Long id,
			@RequestBody Cliente cliente) throws Exception {

		return new ResponseEntity<>(service.atualizarCliente(principal, id, cliente), HttpStatus.OK);
	}

	@PutMapping(value = "/desativar/{id}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> desativarCliente(@AuthenticationPrincipal Jwt principal, @PathVariable Long id)
			throws Exception {

		return new ResponseEntity<>(service.desativarCliente(principal, id), HttpStatus.OK);
	}
}