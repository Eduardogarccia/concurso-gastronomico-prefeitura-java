package br.com.concurso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.concurso.models.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	
	
	boolean existsByNome(String nome);

}
