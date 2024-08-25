package br.com.concurso.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.concurso.exceptions.AvaliacaoDuplicadaException;
import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.models.AvaliacaoPrato;
import br.com.concurso.models.Prato;
import br.com.concurso.repositories.AvaliacaoPratoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AvaliacaoPratoService {

	private final AvaliacaoPratoRepository avaliacaoPratoRepository;
	
	private final PratoService pratoService;
	
	public AvaliacaoPrato salvar(AvaliacaoPrato avaliacaoPrato) {
		
		return avaliacaoPratoRepository.save(avaliacaoPrato);
	}
	
	public AvaliacaoPrato buscarPorId(Long id) {
		return avaliacaoPratoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Avaliação de prato com id " + id + " não encontrado!"));
	}
	
	public List<AvaliacaoPrato> buscarTodos(){
		return avaliacaoPratoRepository.findAll();
	}
	
	public void deletarPorId(Long id) {
		avaliacaoPratoRepository.deleteById(id);
	}
	
	public AvaliacaoPrato editarAvaliacaoPrato(Long id, AvaliacaoPrato avaliacaoPratoAtualizado) throws Exception {
		Optional<AvaliacaoPrato> avaliacaoPratoExistente = Optional.ofNullable(buscarPorId(id));
		if (avaliacaoPratoExistente.isPresent()) {
			
			AvaliacaoPrato avaliacaoPrato = avaliacaoPratoExistente.get();
			
			avaliacaoPrato.setDescricao(avaliacaoPratoAtualizado.getDescricao());
			avaliacaoPrato.setNota(avaliacaoPratoAtualizado.getNota());
			
			return salvar(avaliacaoPrato);
		} else {
			throw new EntityNotFoundException("Avaliação de prato com id " + id + " não encontrado!");
		}
		
	}
	
	public BigDecimal somarNotasPorPrato(Long pratoId) {
        
		return avaliacaoPratoRepository.somarNotasPorPrato(pratoId);
    }
	
	
	public AvaliacaoPrato avaliarPrato(AvaliacaoPrato avaliacaoPrato) {
	   
		Optional<AvaliacaoPrato> avaliacaoExistente = avaliacaoPratoRepository.findByUserIdAndPratoId(avaliacaoPrato.getUser().getId(), avaliacaoPrato.getPrato().getId());
		
		if(avaliacaoExistente.isPresent()) {
			throw new AvaliacaoDuplicadaException("O usuário já avaliou este prato.");
		}
		
		Prato pratoAvaliado = pratoService.buscarPorId(avaliacaoPrato.getPrato().getId());

	    pratoAvaliado.setQtdAvaliacoes(pratoAvaliado.getQtdAvaliacoes() + 1);

	    avaliacaoPratoRepository.save(avaliacaoPrato);

	    BigDecimal somaNotas = somarNotasPorPrato(pratoAvaliado.getId());

	    BigDecimal qtdAvaliacoes = new BigDecimal(pratoAvaliado.getQtdAvaliacoes());

	    if (qtdAvaliacoes.compareTo(BigDecimal.ZERO) > 0) {
	        BigDecimal notaMedia = somaNotas.divide(qtdAvaliacoes, 2, RoundingMode.HALF_UP);

	        pratoAvaliado.setNota(notaMedia);
	        
	        pratoService.atualizarPrato(pratoAvaliado);
	    }

	    return avaliacaoPrato;
	}

	
	
}
