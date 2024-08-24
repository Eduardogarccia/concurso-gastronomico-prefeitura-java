package br.com.concurso.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteResponseDTO {

	private Long id;

	private String nome;
	
	private String endereco;
	
	private BigDecimal nota;
	
	private int qtdAvaliacoes;

}
