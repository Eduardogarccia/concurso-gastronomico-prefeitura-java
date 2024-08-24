package br.com.concurso.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
	
	@NotBlank(message = "O nome do usuário não pode ser nulo !")
	private String nome;
	
	@NotBlank(message = "O email do usuário não pode ser nulo !")
	private String email;
	
	@NotBlank(message = "O cpf do usuário não pode ser nulo !")
	@Size(min = 11, max = 11)
	private String cpf;

}
