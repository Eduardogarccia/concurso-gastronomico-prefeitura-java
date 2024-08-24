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
public class AvaliacaoPratoResponseDTO {
	

	private Long id;
	
	private String descricao;
	
	private BigDecimal nota;
	
	private Long userId;
	
	private Long pratoId;
}
