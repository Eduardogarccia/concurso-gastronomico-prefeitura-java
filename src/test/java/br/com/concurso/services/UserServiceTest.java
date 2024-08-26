package br.com.concurso.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.exceptions.UsernameUniqueViolationException;
import br.com.concurso.models.User;
import br.com.concurso.repositories.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "User Test", "user@test.com", "12345678901", null, null);
    }

    @Test
    void salvarDeveLancarExcecaoQuandoCpfJaExiste() {
        when(userRepository.existsByCpf(user.getCpf())).thenReturn(true);

        UsernameUniqueViolationException exception = assertThrows(UsernameUniqueViolationException.class, () -> {
            userService.salvar(user);
        });

        assertEquals("User com CPF: 12345678901 já cadastrado ou CPF inválido!", exception.getMessage());
    }

    @Test
    void salvarDeveLancarExcecaoQuandoEmailJaExiste() {
        when(userRepository.existsByCpf(user.getCpf())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        UsernameUniqueViolationException exception = assertThrows(UsernameUniqueViolationException.class, () -> {
            userService.salvar(user);
        });

        assertEquals("User com email: user@test.com já cadastrado ou email inválido!", exception.getMessage());
    }

    @Test
    void salvarDeveLancarExcecaoQuandoViolacaoDeIntegridadeDeDados() {
        when(userRepository.existsByCpf(user.getCpf())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        UsernameUniqueViolationException exception = assertThrows(UsernameUniqueViolationException.class, () -> {
            userService.salvar(user);
        });

        assertTrue(exception.getMessage().contains("Erro ao salvar User"));
    }

    @Test
    void salvarDeveSalvarUsuarioCorretamente() {
        when(userRepository.existsByCpf(user.getCpf())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.salvar(user);

        assertNotNull(savedUser);
        assertEquals("User Test", savedUser.getNome());
        assertEquals("user@test.com", savedUser.getEmail());
        assertEquals("12345678901", savedUser.getCpf());
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.buscarPorId(1L);
        });

        assertEquals("Usuário com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void buscarPorIdDeveRetornarUsuarioCorretamente() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.buscarPorId(1L);

        assertNotNull(foundUser);
        assertEquals("User Test", foundUser.getNome());
    }

    @Test
    void editarUserDeveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.editarUser(1L, user);
        });

        assertEquals("Usuário com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void editarUserDeveAtualizarUsuarioCorretamente() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = new User(1L, "Updated User", "updated@test.com", "12345678901", null, null);
        User savedUser = userService.editarUser(1L, updatedUser);

        assertNotNull(savedUser);
        assertEquals("Updated User", savedUser.getNome());
        assertEquals("updated@test.com", savedUser.getEmail());
    }

    @Test
    void deletarPorIdDeveDeletarUsuarioCorretamente() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deletarPorId(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void buscarPorCpfDeveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(userRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.buscarPorCpf("12345678901");
        });

        assertEquals("Usuário com cpf: 12345678901 não encontrado!", exception.getMessage());
    }

    @Test
    void buscarPorCpfDeveRetornarUsuarioCorretamente() {
        when(userRepository.findByCpf("12345678901")).thenReturn(Optional.of(user));

        User foundUser = userService.buscarPorCpf("12345678901");

        assertNotNull(foundUser);
        assertEquals("User Test", foundUser.getNome());
    }
    
    @Test
    void buscarTodosDeveRetornarListaDeUsuarios() {
        List<User> users = List.of(
                new User(1L, "User Test 1", "user1@test.com", "12345678901", null, null),
                new User(2L, "User Test 2", "user2@test.com", "12345678902", null, null)
        );
        
        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.buscarTodos();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        assertEquals("User Test 1", foundUsers.get(0).getNome());
        assertEquals("User Test 2", foundUsers.get(1).getNome());
    }
    

}
