package br.com.concurso.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.concurso.dtos.PratoCreateDTO;
import br.com.concurso.dtos.PratoResponseDTO;
import br.com.concurso.models.Prato;
import br.com.concurso.services.RestauranteService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PratoMapper {
	
	private final RestauranteService restauranteService;

	
	public Prato toPrato(PratoCreateDTO createDTO) {
		Prato prato = new Prato();
		BeanUtils.copyProperties(createDTO, prato);
		prato.setRestaurante(restauranteService.buscarPorId(createDTO.getRestauranteId()));
		return prato;
	}
	
	public PratoResponseDTO toDto(Prato prato) {
		PratoResponseDTO responseDTO = new PratoResponseDTO();
		BeanUtils.copyProperties(prato, responseDTO);
		responseDTO.setRestauranteId(prato.getRestaurante().getId());
		return responseDTO;
	}
	
	public List<PratoResponseDTO> toListDto(List<Prato> pratos){
		return pratos.stream().map(prato -> toDto(prato)).collect(Collectors.toList());
	}
}
