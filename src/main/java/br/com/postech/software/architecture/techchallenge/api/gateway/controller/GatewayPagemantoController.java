//package br.com.postech.software.architecture.techchallenge.api.gateway.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import javax.ws.rs.core.MediaType;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.PagamentoProperties;
//import br.com.postech.software.architecture.techchallenge.api.gateway.service.http.Proxy;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/v1/pagamentos")
//@RequiredArgsConstructor
//public class GatewayPagemantoController {
//
//	private final Proxy proxy;
//	private final PagamentoProperties pagamentoProperties;
//
//	@SuppressWarnings("unchecked")
//	@GetMapping(path = "/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<T> consultarPagamentoPorPedido(@AuthenticationPrincipal Jwt principal,
//			@PathVariable Long numeroPedido) throws Exception {
//		
//		proxy.setJwt(principal);
//		proxy.setResource(pagamentoProperties.getByNumber());
//		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;
//		
//		return new ResponseEntity<>((T)proxy.get(Object.class, pathParam), HttpStatus.OK);
//	}
//
//	@SuppressWarnings("unchecked")
//	@GetMapping(path = "/historico/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<List<T>> consultarHistoricoPagamentoPorPedido(@AuthenticationPrincipal Jwt principal,
//			@PathVariable Long numeroPedido) throws Exception {
//		
//		proxy.setJwt(principal);
//		proxy.setResource(pagamentoProperties.getByNumber());
//		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;
//		
//		return new ResponseEntity<>(proxy.get(ArrayList.class, pathParam), HttpStatus.OK);
//	}
//}