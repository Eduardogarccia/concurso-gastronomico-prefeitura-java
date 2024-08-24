package br.com.concurso.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.concurso.dtos.AvaliacaoRestauranteCreateDTO;
import br.com.concurso.dtos.AvaliacaoRestauranteResponseDTO;
import br.com.concurso.models.AvaliacaoRestaurante;
import br.com.concurso.services.RestauranteService;
import br.com.concurso.services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AvaliacaoRestauranteMapper {
	
	private final UserService userService;
	private final RestauranteService restauranteService;

	public AvaliacaoRestaurante toAvaliacaoRestaurante(AvaliacaoRestauranteCreateDTO createDTO) {
		AvaliacaoRestaurante avaliacaoRestaurante = new AvaliacaoRestaurante();
		BeanUtils.copyProperties(createDTO, avaliacaoRestaurante);
		avaliacaoRestaurante.setUser(userService.buscarPorId(createDTO.getUserId()));
		avaliacaoRestaurante.setRestaurante(restauranteService.buscarPorId(createDTO.getRestauranteId()));
		return avaliacaoRestaurante;
	}
	
	public AvaliacaoRestauranteResponseDTO toDto (AvaliacaoRestaurante avaliacaoRestaurante) {
		AvaliacaoRestauranteResponseDTO responseDTO = new AvaliacaoRestauranteResponseDTO();
		BeanUtils.copyProperties(avaliacaoRestaurante, responseDTO);
		responseDTO.setUserId(avaliacaoRestaurante.getUser().getId());
		responseDTO.setRestauranteId(avaliacaoRestaurante.getRestaurante().getId());
		return responseDTO;
	}
	
	public List<AvaliacaoRestauranteResponseDTO> toListDto(List<AvaliacaoRestaurante> avaliacoesRestaurantes){
		return avaliacoesRestaurantes.stream().map(avaliacaoRestaurante -> toDto(avaliacaoRestaurante)).collect(Collectors.toList());
	}
}
