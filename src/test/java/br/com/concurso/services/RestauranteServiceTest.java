package br.com.concurso.services;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.exceptions.UsernameUniqueViolationException;
import br.com.concurso.models.Restaurante;
import br.com.concurso.repositories.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private RestauranteService restauranteService;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurante = new Restaurante(1L, "Restaurante Test", "Rua Teste", BigDecimal.valueOf(4.5), 10, null, null);
    }

    @Test
    void salvarDeveLancarExcecaoQuandoRestauranteJaExiste() {
        when(restauranteRepository.existsByNome(restaurante.getNome())).thenReturn(true);

        UsernameUniqueViolationException exception = assertThrows(UsernameUniqueViolationException.class, () -> {
            restauranteService.salvar(restaurante);
        });

        assertEquals("Esse restaurante ja foi cadastrado!", exception.getMessage());
    }

    @Test
    void salvarDeveLancarExcecaoQuandoViolacaoDeIntegridadeDeDados() {
        when(restauranteRepository.existsByNome(restaurante.getNome())).thenReturn(false);
        when(restauranteRepository.save(any(Restaurante.class))).thenThrow(DataIntegrityViolationException.class);

        UsernameUniqueViolationException exception = assertThrows(UsernameUniqueViolationException.class, () -> {
            restauranteService.salvar(restaurante);
        });

        assertTrue(exception.getMessage().contains("Erro ao salvar restaurante"));
    }

    @Test
    void salvarDeveSalvarRestauranteCorretamente() {
        when(restauranteRepository.existsByNome(restaurante.getNome())).thenReturn(false);
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        Restaurante savedRestaurante = restauranteService.salvar(restaurante);

        assertNotNull(savedRestaurante);
        assertEquals("Restaurante Test", savedRestaurante.getNome());
        assertEquals("Rua Teste", savedRestaurante.getEndereco());
        assertEquals(BigDecimal.valueOf(4.5), savedRestaurante.getNota());
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoRestauranteNaoExiste() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            restauranteService.buscarPorId(1L);
        });

        assertEquals("Restaurante com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void buscarPorIdDeveRetornarRestauranteCorretamente() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));

        Restaurante foundRestaurante = restauranteService.buscarPorId(1L);

        assertNotNull(foundRestaurante);
        assertEquals("Restaurante Test", foundRestaurante.getNome());
    }

    @Test
    void buscarTodosDeveRetornarListaDeRestaurantes() {
        List<Restaurante> restaurantes = List.of(
                new Restaurante(1L, "Restaurante Test 1", "Rua Teste 1", BigDecimal.valueOf(4.5), 10, null, null),
                new Restaurante(2L, "Restaurante Test 2", "Rua Teste 2", BigDecimal.valueOf(4.0), 8, null, null)
        );

        when(restauranteRepository.findAll()).thenReturn(restaurantes);

        List<Restaurante> foundRestaurantes = restauranteService.buscarTodos();

        assertNotNull(foundRestaurantes);
        assertEquals(2, foundRestaurantes.size());
        assertEquals("Restaurante Test 1", foundRestaurantes.get(0).getNome());
        assertEquals("Restaurante Test 2", foundRestaurantes.get(1).getNome());
    }

    @Test
    void deletarPorIdDeveDeletarRestauranteCorretamente() {
        doNothing().when(restauranteRepository).deleteById(1L);

        restauranteService.deletarPorId(1L);

        verify(restauranteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarPorIdDeveLancarExcecaoQuandoRestauranteNaoExiste() {
        doThrow(new EntityNotFoundException("Restaurante com id 1 não encontrado!"))
                .when(restauranteRepository).deleteById(1L);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            restauranteService.deletarPorId(1L);
        });

        assertEquals("Restaurante com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void editarRestauranteDeveLancarExcecaoQuandoRestauranteNaoExiste() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            restauranteService.editarRestaurante(1L, restaurante);
        });

        assertEquals("Restaurante com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void editarRestauranteDeveAtualizarRestauranteCorretamente() throws Exception {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        Restaurante updatedRestaurante = new Restaurante(1L, "Restaurante Atualizado", "Rua Atualizada", BigDecimal.valueOf(4.7), 15, null, null);
        Restaurante savedRestaurante = restauranteService.editarRestaurante(1L, updatedRestaurante);

        assertNotNull(savedRestaurante);
        assertEquals("Restaurante Atualizado", savedRestaurante.getNome());
        assertEquals("Rua Atualizada", savedRestaurante.getEndereco());
    }


    @Test
    void atualizarRestauranteDeveLancarExcecaoQuandoRestauranteNaoExiste() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            restauranteService.atualizarRestaurante(restaurante);
        });

        assertEquals("Restaurante com id 1 não encontrado.", exception.getMessage());
    }
}
