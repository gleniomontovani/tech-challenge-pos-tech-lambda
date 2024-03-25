package br.com.postech.software.architecture.techchallenge.api.gateway.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.PedidoProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.model.Pedido;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.http.Proxy;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pedidos")
@RequiredArgsConstructor
public class GatewayPedidoController {

	private final Proxy proxy;
	private final PedidoProperties pedidoProperties;

	@SuppressWarnings({ "unchecked" })
	@GetMapping(produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<List<T>> listarTodosPedidos(@AuthenticationPrincipal Jwt principal) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(pedidoProperties.getListall());

		return new ResponseEntity<>(proxy.get(ArrayList.class), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> buscarPedido(@AuthenticationPrincipal Jwt principal, @PathVariable Long numeroPedido)
			throws Exception {

		proxy.setJwt(principal);
		proxy.setResource(pedidoProperties.getByNumber());
		String paramPath = Objects.nonNull(numeroPedido) ? String.valueOf(numeroPedido) : null;

		return new ResponseEntity<>((T) proxy.get(Object.class, paramPath), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(path = "/checkout", produces = MediaType.APPLICATION_JSON)
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> fazerCheckoutFake(@AuthenticationPrincipal Jwt principal, @RequestBody Pedido pedido)
			throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(pedidoProperties.getSave());

		return new ResponseEntity<>((T) proxy.get(Object.class), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PutMapping(path = "/status", produces = MediaType.APPLICATION_JSON, params = { "numeroPedido", "statusPedido" })
	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
	public <T> ResponseEntity<T> atualizarStatusPedido(@AuthenticationPrincipal Jwt principal,
			@RequestParam Long numeroPedido, @RequestParam Integer statusPedido) throws Exception {

		proxy.setJwt(principal);
		proxy.setResource(pedidoProperties.getUpdate());
		List<String> paramPath = Arrays.asList(String.valueOf(numeroPedido), String.valueOf(statusPedido));

		return new ResponseEntity<>((T) proxy.put(paramPath, Object.class), HttpStatus.OK);
	}
}