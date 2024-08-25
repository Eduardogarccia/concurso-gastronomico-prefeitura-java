package br.com.concurso.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.concurso.models.AvaliacaoRestaurante;

public interface AvaliacaoRestauranteRepository extends JpaRepository<AvaliacaoRestaurante, Long> {
	
	@Query("SELECT COALESCE(SUM(ar.nota), 0) FROM AvaliacaoRestaurante ar WHERE ar.restaurante.id = :restauranteId")
	BigDecimal somarNotasPorRestaurante(@Param("restauranteId") Long restauranteId);

}
