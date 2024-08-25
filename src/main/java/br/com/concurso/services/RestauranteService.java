package br.com.concurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.models.Prato;
import br.com.concurso.models.Restaurante;
import br.com.concurso.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RestauranteService {
	
	private final RestauranteRepository restauranteRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		
		return restauranteRepository.save(restaurante);
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
