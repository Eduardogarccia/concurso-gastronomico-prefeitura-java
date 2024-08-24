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

import br.com.concurso.dtos.PratoCreateDTO;
import br.com.concurso.dtos.PratoResponseDTO;
import br.com.concurso.mappers.PratoMapper;
import br.com.concurso.models.Prato;
import br.com.concurso.services.PratoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("concurso/prato")
public class PratoController {
	
	private final PratoService pratoService;
	
	private final PratoMapper pratoMapper;
	
	@PostMapping
	public ResponseEntity<PratoResponseDTO> salvar(@RequestBody @Valid PratoCreateDTO createDTO){
		Prato prato = pratoService.salvar(pratoMapper.toPrato(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(pratoMapper.toDto(prato));
	}
	
	@GetMapping
	public ResponseEntity<List<PratoResponseDTO>> obterTodos(){
		List<PratoResponseDTO> pratoResponseDTO = pratoMapper.toListDto(pratoService.buscarTodos());
		return ResponseEntity.status(HttpStatus.OK).body(pratoResponseDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PratoResponseDTO> buscarPorId(@PathVariable Long id){
		PratoResponseDTO pratoResponseDTO = pratoMapper.toDto(pratoService.buscarPorId(id));
		return ResponseEntity.status(HttpStatus.OK).body(pratoResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarPorId(@PathVariable Long id){
		pratoService.deletarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body("Prato com id " + id + " deletado");
	}

}
