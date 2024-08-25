package br.com.concurso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.models.User;
import br.com.concurso.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public User salvar(User user) {
		
		return userRepository.save(user);
	}
	
	public User buscarPorId(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id " + id + " não encontrado!"));
	}
	
	public List<User> buscarTodos(){
		return userRepository.findAll();
	}
	
	public void deletarPorId(Long id) {
		userRepository.deleteById(id);
	}
	
	public User editarUser(Long id, User userAtualizado) throws Exception {
		Optional<User> userExistente = Optional.ofNullable(buscarPorId(id));
		if (userExistente.isPresent()) {
			User user = userExistente.get();
			
			user.setCpf(userAtualizado.getCpf());
			user.setEmail(userAtualizado.getEmail());
			user.setNome(userAtualizado.getNome());
			
			return salvar(user);
		} else {
			throw new EntityNotFoundException("Usuário com id " + id + " não encontrado!");
		}
		
	}
	
	public User buscarPorCpf(String cpf) {
		return userRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Usuário com cpf: " + cpf + " não encontrado!"));
	}

}
