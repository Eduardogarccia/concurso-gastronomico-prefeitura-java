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

import br.com.concurso.dtos.RestauranteCreateDTO;
import br.com.concurso.dtos.RestauranteResponseDTO;
import br.com.concurso.mappers.RestauranteMapper;
import br.com.concurso.models.Restaurante;
import br.com.concurso.services.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("concurso/restaurante")
public class RestauranteController {
	
	private final RestauranteService restauranteService;
	
	private final RestauranteMapper restauranteMapper;
	
	@PostMapping
	public ResponseEntity<RestauranteResponseDTO> salvar(@RequestBody @Valid RestauranteCreateDTO createDTO){
		Restaurante restaurante = restauranteService.salvar(restauranteMapper.toRestaurante(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(restauranteMapper.toDto(restaurante));
	}
	
	@GetMapping
	public ResponseEntity<List<RestauranteResponseDTO>> obterTodos(){
		List<RestauranteResponseDTO> restauranteResponseDTO = restauranteMapper.toListDto(restauranteService.buscarTodos());
		return ResponseEntity.status(HttpStatus.OK).body(restauranteResponseDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id){
		RestauranteResponseDTO restauranteResponseDTO = restauranteMapper.toDto(restauranteService.buscarPorId(id));
		return ResponseEntity.status(HttpStatus.OK).body(restauranteResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarPorId(@PathVariable Long id){
		restauranteService.deletarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body("Restaurante com id " + id + " deletado");
	}

}
