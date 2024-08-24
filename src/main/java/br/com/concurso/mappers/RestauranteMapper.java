package br.com.concurso.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.concurso.dtos.RestauranteCreateDTO;
import br.com.concurso.dtos.RestauranteResponseDTO;
import br.com.concurso.models.Restaurante;

@Component
public class RestauranteMapper {

	public Restaurante toRestaurante(RestauranteCreateDTO createDTO) {
		Restaurante restaurante = new Restaurante();
		BeanUtils.copyProperties(createDTO, restaurante);
		return restaurante;
	}
	
	public RestauranteResponseDTO toDto(Restaurante restaurante) {
		RestauranteResponseDTO responseDTO = new RestauranteResponseDTO();
		BeanUtils.copyProperties(restaurante, responseDTO);
		return responseDTO;
		
	}
	
	public List<RestauranteResponseDTO> toListDto(List<Restaurante> restaurantes){
		return restaurantes.stream().map(restaurante -> toDto(restaurante)).collect(Collectors.toList());
	}
}
