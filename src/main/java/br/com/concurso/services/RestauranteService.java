package br.com.concurso.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.exceptions.UsernameUniqueViolationException;
import br.com.concurso.exceptions.ValoresIniciaisInvalidosException;
import br.com.concurso.models.Restaurante;
import br.com.concurso.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RestauranteService {
	
	private final RestauranteRepository restauranteRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		
		if(restauranteRepository.existsByNome(restaurante.getNome())) {
			throw new UsernameUniqueViolationException("Esse restaurante ja foi cadastrado!");
		}
		
		if(restaurante.getNota() != BigDecimal.ZERO || restaurante.getQtdAvaliacoes() != 0) {
			throw new ValoresIniciaisInvalidosException("A nota e a quantidade de avaliações iniciais devem ser igual a zero!");
		}
		
		try {
			return restauranteRepository.save(restaurante);	
		} catch (DataIntegrityViolationException e) {
			throw new UsernameUniqueViolationException("Erro ao salvar restaurante. " + e.getMessage());
		}
		
	}
	
	public Restaurante buscarPorId(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurante com id " + id + " não encontrado!"));
	}
	
	public List<Restaurante> buscarTodos(){
		return restauranteRepository.findAll();
	}
	
	public void deletarPorId(Long id) {
		restauranteRepository.deleteById(id);
	}
	
	public Restaurante editarRestaurante(Long id, Restaurante restauranteAtualizado) throws Exception {
		Optional<Restaurante> restauranteExistente = Optional.ofNullable(buscarPorId(id));
		if (restauranteExistente.isPresent()) {
			
			Restaurante restaurante = restauranteExistente.get();
			
			restaurante.setNome(restauranteAtualizado.getNome());
			restaurante.setEndereco(restauranteAtualizado.getEndereco());
			
			return salvar(restaurante);
		} else {
			throw new EntityNotFoundException("Restaurante com id " + id + " não encontrado!");
		}
		
	}
	
	public Restaurante atualizarRestaurante(Restaurante restauranteAtualizado) {
        Optional<Restaurante> restauranteExistente = restauranteRepository.findById(restauranteAtualizado.getId());

        if (restauranteExistente.isPresent()) {
            Restaurante restaurante = restauranteRepository.save(restauranteAtualizado);
            return restaurante;
        } else {
            throw new EntityNotFoundException("Restaurante com id " + restauranteAtualizado.getId() + " não encontrado.");
        }
    }

}
