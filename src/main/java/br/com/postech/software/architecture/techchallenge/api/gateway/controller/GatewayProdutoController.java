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
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ProdutoProperties;
//import br.com.postech.software.architecture.techchallenge.api.gateway.model.Produto;
//import br.com.postech.software.architecture.techchallenge.api.gateway.service.http.Proxy;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/v1/produtos")
//@RequiredArgsConstructor
//public class GatewayProdutoController {
//
//	private final Proxy proxy;
//	private final ProdutoProperties produtoProperties;
//
//	@SuppressWarnings("unchecked")
//	@GetMapping(produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<List<T>> listarProdutos(@AuthenticationPrincipal Jwt principal, Integer categoria)
//			throws Exception {
//		proxy.setJwt(principal);
//		proxy.setResource(produtoProperties.getListall());
//		String pathParam = Objects.nonNull(categoria) ? String.valueOf(categoria) : null;
//
//		return new ResponseEntity<>(proxy.get(ArrayList.class, pathParam), HttpStatus.OK);
//	}
//
//	@SuppressWarnings("unchecked")
//	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<T> buscarProdutoPorId(@AuthenticationPrincipal Jwt principal, @PathVariable Long id)
//			throws Exception {
//		proxy.setJwt(principal);
//		proxy.setResource(produtoProperties.getByid());
//		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;
//
//		return new ResponseEntity<>((T) proxy.get(Object.class, pathParam), HttpStatus.OK);
//	}
//
//	@SuppressWarnings("unchecked")
//	@PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<T> salvar(@AuthenticationPrincipal Jwt principal, @RequestBody Produto produto)
//			throws Exception {
//		proxy.setJwt(principal);
//		proxy.setResource(produtoProperties.getSave());
//
//		return new ResponseEntity<>((T) proxy.post(produto), HttpStatus.OK);
//	}
//
//	@SuppressWarnings("unchecked")
//	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<T> atualizar(@AuthenticationPrincipal Jwt principal, @PathVariable Integer id,
//			@RequestBody Produto produto) throws Exception {
//
//		proxy.setJwt(principal);
//		proxy.setResource(produtoProperties.getUpdate());
//		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;
//
//		return new ResponseEntity<>((T) proxy.put(produto, pathParam), HttpStatus.OK);
//	}
//
//	@SuppressWarnings("unchecked")
//	@DeleteMapping("/{id}")
//	@PreAuthorize("hasAuthority('SCOPE_fiap/postech/techchallenge')")
//	public <T> ResponseEntity<T> deleteById(@AuthenticationPrincipal Jwt principal, @PathVariable Long id)
//			throws Exception {
//		proxy.setJwt(principal);
//		proxy.setResource(produtoProperties.getDelete());
//		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;
//
//		return new ResponseEntity<>((T) proxy.delete(pathParam), HttpStatus.OK);
//	}
//}
