package br.com.concurso.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.exceptions.RestauranteJaTemPratoException;
import br.com.concurso.exceptions.ValoresIniciaisInvalidosException;
import br.com.concurso.models.Prato;
import br.com.concurso.repositories.PratoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PratoService {

	private final PratoRepository pratoRepository;
	
	public Prato salvar(Prato prato) {
		

		if(pratoRepository.existsByRestauranteId(prato.getRestaurante().getId())) {
			throw new RestauranteJaTemPratoException("Esse restaurante já tem um prato cadastrado!");
		}
		
		if(prato.getNota() != BigDecimal.ZERO || prato.getQtdAvaliacoes() != 0) {
			throw new ValoresIniciaisInvalidosException("A nota e a quantidade de avaliações iniciais devem ser igual a zero!");
		}
		
		return pratoRepository.save(prato);
	}
	
	public Prato buscarPorId(Long id) {
		return pratoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prato com id " + id + " não encontrado!"));
	}
	
	public List<Prato> buscarTodos(){
		return pratoRepository.findAll();
	}
	
	public void deletarPorId(Long id) {
		pratoRepository.deleteById(id);
	}
	
	public Prato editarPrato(Long id, Prato pratoAtualizado) throws Exception {
		Optional<Prato> pratoExistente = Optional.ofNullable(buscarPorId(id));
		if (pratoExistente.isPresent()) {
			
			Prato prato = pratoExistente.get();
			
			prato.setNome(pratoAtualizado.getNome());
			prato.setDescricao(pratoAtualizado.getDescricao());
			
			return salvar(prato);
		} else {
			throw new EntityNotFoundException("Prato com id " + id + " não encontrado!");
		}
		
	}
	
	public Prato atualizarPrato(Prato pratoAvaliado) {
     
        Optional<Prato> pratoExistente = pratoRepository.findById(pratoAvaliado.getId());

        if (pratoExistente.isPresent()) {
         
            Prato pratoAtualizado = pratoRepository.save(pratoAvaliado);
            return pratoAtualizado;
        } else {
            throw new EntityNotFoundException("Prato com id " + pratoAvaliado.getId() + " não encontrado.");
        }
    }

}
