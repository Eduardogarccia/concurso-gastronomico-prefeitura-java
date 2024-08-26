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

import br.com.concurso.dtos.AvaliacaoRestauranteCreateDTO;
import br.com.concurso.dtos.AvaliacaoRestauranteResponseDTO;
import br.com.concurso.mappers.AvaliacaoRestauranteMapper;
import br.com.concurso.models.AvaliacaoRestaurante;
import br.com.concurso.services.AvaliacaoRestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@RequiredArgsConstructor
@RestController
@RequestMapping("concurso/avaliacao/restaurante")
public class AvaliacaoRestauranteController {

	private final AvaliacaoRestauranteService avaliacaoRestauranteService;
	
	private final AvaliacaoRestauranteMapper avaliacaoRestauranteMapper;
	
	@PostMapping
	public ResponseEntity<AvaliacaoRestauranteResponseDTO> avaliarRestaurante(@RequestBody @Valid AvaliacaoRestauranteCreateDTO createDTO){
		AvaliacaoRestaurante avaliacaoRestaurante = avaliacaoRestauranteService.avaliarRestaurante(avaliacaoRestauranteMapper.toAvaliacaoRestaurante(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoRestauranteMapper.toDto(avaliacaoRestaurante));
	}
	
	@GetMapping
	public ResponseEntity<List<AvaliacaoRestauranteResponseDTO>> obterTodos(){
		List<AvaliacaoRestauranteResponseDTO> avaliacaoRestauranteResponseDTO = avaliacaoRestauranteMapper.toListDto(avaliacaoRestauranteService.buscarTodos());
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoRestauranteResponseDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AvaliacaoRestauranteResponseDTO> buscarPorId(@PathVariable Long id){
		AvaliacaoRestauranteResponseDTO avaliacaoRestauranteResponseDTO = avaliacaoRestauranteMapper.toDto(avaliacaoRestauranteService.buscarPorId(id));
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoRestauranteResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarPorId(@PathVariable Long id){
		avaliacaoRestauranteService.deletarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body("Avaliação de restaurante com id " + id + " deletado");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AvaliacaoRestauranteResponseDTO> editarPorId(@PathVariable Long id, @RequestBody AvaliacaoRestauranteCreateDTO createDTO) throws Exception{
		AvaliacaoRestaurante avaliacaoRestaurante = avaliacaoRestauranteService.editarAvaliacaoRestaurante(id, avaliacaoRestauranteMapper.toAvaliacaoRestaurante(createDTO));
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoRestauranteMapper.toDto(avaliacaoRestaurante));
		
	}
	
}
