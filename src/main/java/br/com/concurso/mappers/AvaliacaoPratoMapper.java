package br.com.concurso.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.concurso.dtos.AvaliacaoPratoCreateDTO;
import br.com.concurso.dtos.AvaliacaoPratoResponseDTO;
import br.com.concurso.models.AvaliacaoPrato;
import br.com.concurso.services.PratoService;
import br.com.concurso.services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AvaliacaoPratoMapper {
	
	private final UserService userService;
	private final PratoService pratoService;


	public AvaliacaoPrato toAvaliacaoPrato(AvaliacaoPratoCreateDTO createDTO) {
		AvaliacaoPrato avaliacaoPrato = new AvaliacaoPrato();
		BeanUtils.copyProperties(createDTO, avaliacaoPrato);
		avaliacaoPrato.setUser(userService.buscarPorId(createDTO.getUserId()));
		avaliacaoPrato.setPrato(pratoService.buscarPorId(createDTO.getPratoId()));
		return avaliacaoPrato;
	}
	
	public AvaliacaoPratoResponseDTO toDto (AvaliacaoPrato avaliacaoPrato) {
		AvaliacaoPratoResponseDTO responseDTO = new AvaliacaoPratoResponseDTO();
		BeanUtils.copyProperties(avaliacaoPrato, responseDTO);
		responseDTO.setUserId(avaliacaoPrato.getUser().getId());
		responseDTO.setPratoId(avaliacaoPrato.getPrato().getId());
		return responseDTO;
	}
	
	public List<AvaliacaoPratoResponseDTO> toListDto(List<AvaliacaoPrato> avaliacoesPratos){
		return avaliacoesPratos.stream().map(avaliacaoPrato -> toDto(avaliacaoPrato)).collect(Collectors.toList());
	}
}
