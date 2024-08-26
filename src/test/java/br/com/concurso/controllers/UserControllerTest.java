package br.com.concurso.controllers;

import br.com.concurso.dtos.UserCreateDTO;
import br.com.concurso.dtos.UserResponseDTO;
import br.com.concurso.mappers.UserMapper;
import br.com.concurso.models.User;
import br.com.concurso.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "User Test", "user@test.com", "12345678901", null, null);
        userResponseDTO = new UserResponseDTO(1L, "User Test", "user@test.com", "12345678901");
    }

    @Test
    void salvarDeveRetornarStatusCreatedComUserResponseDTO() {
        UserCreateDTO createDTO = new UserCreateDTO("User Test", "user@test.com", "12345678901");

        when(userMapper.toUser(any(UserCreateDTO.class))).thenReturn(user);
        when(userService.salvar(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.salvar(createDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponseDTO, response.getBody());

        verify(userService, times(1)).salvar(any(User.class));
        verify(userMapper, times(1)).toUser(any(UserCreateDTO.class));
        verify(userMapper, times(1)).toDto(any(User.class));
    }

    @Test
    void obterTodosDeveRetornarStatusOkComListaDeUserResponseDTO() {
        List<UserResponseDTO> userResponseDTOList = List.of(userResponseDTO);

        when(userService.buscarTodos()).thenReturn(List.of(user));
        when(userMapper.toListDto(anyList())).thenReturn(userResponseDTOList);

        ResponseEntity<List<UserResponseDTO>> response = userController.obterTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDTOList, response.getBody());

        verify(userService, times(1)).buscarTodos();
        verify(userMapper, times(1)).toListDto(anyList());
    }

    @Test
    void buscarPorIdDeveRetornarStatusOkComUserResponseDTO() {
        when(userService.buscarPorId(anyLong())).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDTO, response.getBody());

        verify(userService, times(1)).buscarPorId(anyLong());
        verify(userMapper, times(1)).toDto(any(User.class));
    }

    @Test
    void buscarPorCpfDeveRetornarStatusOkComUserResponseDTO() {
        when(userService.buscarPorCpf(anyString())).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.buscarPorCpf("12345678901");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDTO, response.getBody());

        verify(userService, times(1)).buscarPorCpf(anyString());
        verify(userMapper, times(1)).toDto(any(User.class));
    }

    @Test
    void deletarPorIdDeveRetornarStatusOkComMensagem() {
        doNothing().when(userService).deletarPorId(anyLong());

        ResponseEntity<String> response = userController.deletarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário com id 1 deletado", response.getBody());

        verify(userService, times(1)).deletarPorId(anyLong());
    }
}
