package br.com.concurso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.concurso.models.AvaliacaoRestaurante;

public interface AvaliacaoRestauranteRepository extends JpaRepository<AvaliacaoRestaurante, Long> {

}
