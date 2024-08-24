package br.com.concurso.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PratoCreateDTO {
	
	@NotNull(message = "O nome do prato não pode se nulo !")
	private String nome;
	
	@NotNull(message = "A descrição do prato não pode se nula !")
	private String descricao;
	
	@NotNull(message = "O preço do prato não pode se nulo !")
	private BigDecimal preco;

	@NotNull(message = "O prato precisa ter um restaurante id !")
	private Long restauranteId;
	
	private BigDecimal nota;
	
	private int qtdAvaliacoes;


}
