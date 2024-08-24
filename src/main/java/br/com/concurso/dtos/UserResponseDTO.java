package br.com.concurso.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {
	
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String cpf;

}
