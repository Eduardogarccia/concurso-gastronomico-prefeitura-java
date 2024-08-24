package br.com.concurso.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PratoResponseDTO {
	
	private Long id;

	private String nome;
	
	private String descricao;

	private BigDecimal preco;
	
	private int qtdAvaliacoes;

	private BigDecimal nota;
	
	private Long restauranteId;

}
