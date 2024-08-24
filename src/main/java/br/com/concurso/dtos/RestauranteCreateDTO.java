package br.com.concurso.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteCreateDTO {
	
	@NotBlank(message = "O nome do restaurante não pode ser nulo !")
	private String nome;
	
	@NotBlank(message = "O endereco do restaurante não pode ser nulo !")
	private String endereco;
	
	private BigDecimal nota;
	
	private int qtdAvaliacoes;
	

}
