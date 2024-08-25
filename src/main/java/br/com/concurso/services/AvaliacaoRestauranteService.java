package br.com.concurso.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.models.AvaliacaoRestaurante;
import br.com.concurso.models.Restaurante;
import br.com.concurso.repositories.AvaliacaoRestauranteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AvaliacaoRestauranteService {

	private final AvaliacaoRestauranteRepository avaliacaoRestauranteRepository;
	private final RestauranteService restauranteService;
	
	public AvaliacaoRestaurante salvar(AvaliacaoRestaurante avaliacaoRestaurante) {
		
		return avaliacaoRestauranteRepository.save(avaliacaoRestaurante);
	}
	
	public AvaliacaoRestaurante buscarPorId(Long id) {
		return avaliacaoRestauranteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Avaliação de restaurante com id " + id + " não encontrado!"));
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
			throw new EntityNotFoundException("Avaliação de restaurante com id " + id + " não encontrado!");
		}
		
	}
	
public BigDecimal somarNotasPorRestaurante(Long pratoId) {
        
		return avaliacaoRestauranteRepository.somarNotasPorRestaurante(pratoId);
    }
	
	
	public AvaliacaoRestaurante avaliarRestaurante(AvaliacaoRestaurante avaliacaoRestaurante) {
	    
	    Restaurante restauranteAvaliado = restauranteService.buscarPorId(avaliacaoRestaurante.getRestaurante().getId());

	    restauranteAvaliado.setQtdAvaliacoes(restauranteAvaliado.getQtdAvaliacoes() + 1);

	    avaliacaoRestauranteRepository.save(avaliacaoRestaurante);

	    BigDecimal somaNotas = somarNotasPorRestaurante(restauranteAvaliado.getId());

	    BigDecimal qtdAvaliacoes = new BigDecimal(restauranteAvaliado.getQtdAvaliacoes());

	    if (qtdAvaliacoes.compareTo(BigDecimal.ZERO) > 0) {
	        BigDecimal notaMedia = somaNotas.divide(qtdAvaliacoes, 2, RoundingMode.HALF_UP);

	        restauranteAvaliado.setNota(notaMedia);
	        
	        restauranteService.atualizarRestaurante(restauranteAvaliado);
	    }

	    return avaliacaoRestaurante;
	}
}
