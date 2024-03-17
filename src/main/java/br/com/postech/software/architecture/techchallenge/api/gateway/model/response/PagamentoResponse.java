package br.com.postech.software.architecture.techchallenge.api.gateway.model.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoResponse {

	private Long numeroPagamento;
	private Long numeroPedido;
	private String dataPagamento;
	private Integer statusPagamento;
	private BigDecimal valor;
}
