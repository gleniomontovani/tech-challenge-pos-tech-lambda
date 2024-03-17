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
public class ProdutoResponse {

	private Long id;
    private String nome;
//    private CategoriaEnum categoria;
    private BigDecimal valor;
    private String descricao;
//    private List<ProdutoImagem> imagens;
}
