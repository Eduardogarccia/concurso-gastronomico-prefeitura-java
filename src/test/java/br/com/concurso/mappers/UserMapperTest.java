package br.com.concurso.mappers;

import br.com.concurso.dtos.UserCreateDTO;
import br.com.concurso.dtos.UserResponseDTO;
import br.com.concurso.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void toUserDeveConverterUserCreateDTOParaUser() {
        UserCreateDTO createDTO = new UserCreateDTO("Nome Teste", "teste@teste.com", "12345678901");

        User user = userMapper.toUser(createDTO);

        assertNotNull(user);
        assertEquals("Nome Teste", user.getNome());
        assertEquals("teste@teste.com", user.getEmail());
        assertEquals("12345678901", user.getCpf());
    }

    @Test
    void toDtoDeveConverterUserParaUserResponseDTO() {
        User user = new User(1L, "Nome Teste", "teste@teste.com", "12345678901", null, null);

        UserResponseDTO responseDTO = userMapper.toDto(user);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Nome Teste", responseDTO.getNome());
        assertEquals("teste@teste.com", responseDTO.getEmail());
        assertEquals("12345678901", responseDTO.getCpf());
    }

    @Test
    void toListDtoDeveConverterListaDeUsersParaListaDeUserResponseDTO() {
        List<User> users = List.of(
                new User(1L, "Nome Teste 1", "teste1@teste.com", "12345678901", null, null),
                new User(2L, "Nome Teste 2", "teste2@teste.com", "12345678902", null, null)
        );

        List<UserResponseDTO> dtos = userMapper.toListDto(users);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertEquals("Nome Teste 1", dtos.get(0).getNome());
        assertEquals("teste1@teste.com", dtos.get(0).getEmail());
        assertEquals("12345678901", dtos.get(0).getCpf());

        assertEquals(2L, dtos.get(1).getId());
        assertEquals("Nome Teste 2", dtos.get(1).getNome());
        assertEquals("teste2@teste.com", dtos.get(1).getEmail());
        assertEquals("12345678902", dtos.get(1).getCpf());
    }
}
