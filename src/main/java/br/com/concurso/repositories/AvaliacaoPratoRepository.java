package br.com.concurso.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.concurso.models.AvaliacaoPrato;

public interface AvaliacaoPratoRepository extends JpaRepository<AvaliacaoPrato, Long> {
	
	@Query("SELECT COALESCE(SUM(a.nota), 0) FROM AvaliacaoPrato a WHERE a.prato.id = :pratoId")
	BigDecimal somarNotasPorPrato(@Param("pratoId") Long pratoId);

}
