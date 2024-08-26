package br.com.concurso.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.concurso.models.Prato;

public interface PratoRepository extends JpaRepository<Prato, Long>{

	boolean existsByRestauranteId(Long restauranteId);

}
