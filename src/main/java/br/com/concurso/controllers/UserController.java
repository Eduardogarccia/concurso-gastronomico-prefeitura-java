package br.com.concurso.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.concurso.dtos.UserCreateDTO;
import br.com.concurso.dtos.UserResponseDTO;
import br.com.concurso.mappers.UserMapper;
import br.com.concurso.models.User;
import br.com.concurso.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@RestController
@RequestMapping("concurso/user")
public class UserController {
	
	private final UserService userService;
	
	private final UserMapper userMapper;
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> salvar(@RequestBody @Valid UserCreateDTO createDTO){
		User user = userService.salvar(userMapper.toUser(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> obterTodos(){
		List<UserResponseDTO> userResponseDTO = userMapper.toListDto(userService.buscarTodos());
		return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id){
		UserResponseDTO userResponseDTO = userMapper.toDto(userService.buscarPorId(id));
		return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
	}
	
	@GetMapping("cpf/{cpf}")
	public ResponseEntity<UserResponseDTO> buscarPorCpf(@PathVariable String cpf){
		UserResponseDTO userResponseDTO = userMapper.toDto(userService.buscarPorCpf(cpf));
		return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
 	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarPorId(@PathVariable Long id){
		userService.deletarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body("Usuário com id " + id + " deletado");
	}
	
	
	
	

}
