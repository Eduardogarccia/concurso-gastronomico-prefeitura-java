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

import br.com.concurso.dtos.AvaliacaoPratoCreateDTO;
import br.com.concurso.dtos.AvaliacaoPratoResponseDTO;
import br.com.concurso.mappers.AvaliacaoPratoMapper;
import br.com.concurso.models.AvaliacaoPrato;
import br.com.concurso.services.AvaliacaoPratoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("concurso/avaliacao/prato")
public class AvaliacaoPratoController {

	private final AvaliacaoPratoService avaliacaoPratoService;
	
	private final AvaliacaoPratoMapper avaliacaoPratoMapper;
	
	
	@GetMapping
	public ResponseEntity<List<AvaliacaoPratoResponseDTO>> obterTodos(){
		List<AvaliacaoPratoResponseDTO> avaliacaoPratoResponseDTO = avaliacaoPratoMapper.toListDto(avaliacaoPratoService.buscarTodos());
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoPratoResponseDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AvaliacaoPratoResponseDTO> buscarPorId(@PathVariable Long id){
		AvaliacaoPratoResponseDTO avaliacaoPratoResponseDTO = avaliacaoPratoMapper.toDto(avaliacaoPratoService.buscarPorId(id));
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoPratoResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarPorId(@PathVariable Long id){
		avaliacaoPratoService.deletarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body("Avaliação de prato com id " + id + " deletado");
	}
	
	@PostMapping
	public ResponseEntity<AvaliacaoPratoResponseDTO> avaliarPrato(@RequestBody @Valid AvaliacaoPratoCreateDTO createDTO){
		AvaliacaoPrato avaliacaoPrato = avaliacaoPratoService.avaliarPrato(avaliacaoPratoMapper.toAvaliacaoPrato(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoPratoMapper.toDto(avaliacaoPrato));
	}

}
