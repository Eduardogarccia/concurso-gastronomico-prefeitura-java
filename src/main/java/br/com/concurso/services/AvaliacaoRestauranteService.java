package br.com.concurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.models.AvaliacaoRestaurante;
import br.com.concurso.repositories.AvaliacaoRestauranteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AvaliacaoRestauranteService {

	private final AvaliacaoRestauranteRepository avaliacaoRestauranteRepository;
	
	public AvaliacaoRestaurante salvar(AvaliacaoRestaurante avaliacaoRestaurante) {
		
		return avaliacaoRestauranteRepository.save(avaliacaoRestaurante);
	}
	
	public AvaliacaoRestaurante buscarPorId(Long id) {
		return avaliacaoRestauranteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id" + id + " não encontrado!"));
	}
	
	public List<AvaliacaoRestaurante> buscarTodos(){
		return avaliacaoRestauranteRepository.findAll();
	}
	
	public void deletarPorId(Long id) {
		avaliacaoRestauranteRepository.deleteById(id);
	}
	
	public AvaliacaoRestaurante editarAvaliacaoRestaurante(Long id, AvaliacaoRestaurante avaliacaoRestauranteAtualizado) throws Exception {
		Optional<AvaliacaoRestaurante> avaliacaoRestauranteExistente = Optional.ofNullable(buscarPorId(id));
		if (avaliacaoRestauranteExistente.isPresent()) {
			
			AvaliacaoRestaurante avaliacaoRestaurante = avaliacaoRestauranteExistente.get();
			
			avaliacaoRestaurante.setDescricao(avaliacaoRestauranteAtualizado.getDescricao());
			avaliacaoRestaurante.setNota(avaliacaoRestauranteAtualizado.getNota());
			
			return salvar(avaliacaoRestaurante);
		} else {
			throw new EntityNotFoundException("Usuário com id" + id + " não encontrado!");
		}
		
	}
}
