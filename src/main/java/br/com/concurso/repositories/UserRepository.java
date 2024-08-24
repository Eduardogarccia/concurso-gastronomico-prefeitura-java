package br.com.concurso.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.concurso.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByCpf(String cpf);

}
