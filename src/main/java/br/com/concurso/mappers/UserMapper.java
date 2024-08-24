package br.com.concurso.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.concurso.dtos.UserCreateDTO;
import br.com.concurso.dtos.UserResponseDTO;
import br.com.concurso.models.User;

@Component
public class UserMapper {
	
	public User toUser(UserCreateDTO createDTO) {
		User user = new User();
		BeanUtils.copyProperties(createDTO, user);
		return user;
	}
	
	public UserResponseDTO toDto(User user) {
		UserResponseDTO responseDTO = new UserResponseDTO();
		BeanUtils.copyProperties(user, responseDTO);
		return responseDTO;
		
	}
	
	public List<UserResponseDTO> toListDto(List<User> users){
		return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
	}

}
