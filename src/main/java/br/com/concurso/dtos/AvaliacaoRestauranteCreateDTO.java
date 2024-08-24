package br.com.concurso.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvaliacaoRestauranteCreateDTO {
	
	@NotNull(message = "A descrição da avaliação não pode se nula !")
	private String descricao;
	
	@NotNull(message = "A nota da avaliação não pode se nula !")
	private BigDecimal nota;
	
	@NotNull(message = "A avaliação precisa ter um user id !")
	private Long userId;
	
	@NotNull(message = "A avaliação precisa ter um restaurante id !")
	private Long restauranteId;

}
