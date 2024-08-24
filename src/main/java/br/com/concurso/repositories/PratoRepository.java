package br.com.concurso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.concurso.models.Prato;

public interface PratoRepository extends JpaRepository<Prato, Long>{

}
