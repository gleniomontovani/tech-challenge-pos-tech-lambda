package br.com.postech.software.architecture.techchallenge.api.gateway.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {

	private Long numeroPedido;
//    private Cliente cliente;
    private String dataPedido;
    private Integer statusPedido;
    private String statusPagamento;
//    private List<PedidoProduto> produtos;
}
