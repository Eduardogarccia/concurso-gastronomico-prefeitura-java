package br.com.concurso.services;

import br.com.concurso.exceptions.AvaliacaoDuplicadaException;
import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.models.AvaliacaoRestaurante;
import br.com.concurso.models.Restaurante;
import br.com.concurso.models.User;
import br.com.concurso.repositories.AvaliacaoRestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvaliacaoRestauranteServiceTest {

    @Mock
    private AvaliacaoRestauranteRepository avaliacaoRestauranteRepository;

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private AvaliacaoRestauranteService avaliacaoRestauranteService;

    private AvaliacaoRestaurante avaliacaoRestaurante;
    private Restaurante restaurante;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "User Test", "user@test.com", "12345678901", null, null);
        restaurante = new Restaurante(1L, "Restaurante Test", "Rua Teste", BigDecimal.valueOf(4.5), 10, null, null);
        avaliacaoRestaurante = new AvaliacaoRestaurante(1L, "Boa avaliação", BigDecimal.valueOf(4.0), user, restaurante);
    }

    @Test
    void salvarDeveSalvarAvaliacaoCorretamente() {
        when(avaliacaoRestauranteRepository.save(any(AvaliacaoRestaurante.class))).thenReturn(avaliacaoRestaurante);

        AvaliacaoRestaurante savedAvaliacao = avaliacaoRestauranteService.salvar(avaliacaoRestaurante);

        assertNotNull(savedAvaliacao);
        assertEquals("Boa avaliação", savedAvaliacao.getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), savedAvaliacao.getNota());
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoAvaliacaoNaoExiste() {
        when(avaliacaoRestauranteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            avaliacaoRestauranteService.buscarPorId(1L);
        });

        assertEquals("Avaliação de restaurante com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void buscarPorIdDeveRetornarAvaliacaoCorretamente() {
        when(avaliacaoRestauranteRepository.findById(1L)).thenReturn(Optional.of(avaliacaoRestaurante));

        AvaliacaoRestaurante foundAvaliacao = avaliacaoRestauranteService.buscarPorId(1L);

        assertNotNull(foundAvaliacao);
        assertEquals("Boa avaliação", foundAvaliacao.getDescricao());
    }

    @Test
    void buscarTodosDeveRetornarListaDeAvaliacoes() {
        List<AvaliacaoRestaurante> avaliacoes = List.of(
                new AvaliacaoRestaurante(1L, "Avaliação 1", BigDecimal.valueOf(4.0), user, restaurante),
                new AvaliacaoRestaurante(2L, "Avaliação 2", BigDecimal.valueOf(5.0), user, restaurante)
        );

        when(avaliacaoRestauranteRepository.findAll()).thenReturn(avaliacoes);

        List<AvaliacaoRestaurante> foundAvaliacoes = avaliacaoRestauranteService.buscarTodos();

        assertNotNull(foundAvaliacoes);
        assertEquals(2, foundAvaliacoes.size());
        assertEquals("Avaliação 1", foundAvaliacoes.get(0).getDescricao());
        assertEquals("Avaliação 2", foundAvaliacoes.get(1).getDescricao());
    }

    @Test
    void deletarPorIdDeveDeletarAvaliacaoCorretamente() {
        doNothing().when(avaliacaoRestauranteRepository).deleteById(1L);

        avaliacaoRestauranteService.deletarPorId(1L);

        verify(avaliacaoRestauranteRepository, times(1)).deleteById(1L);
    }

    @Test
    void editarAvaliacaoRestauranteDeveLancarExcecaoQuandoAvaliacaoNaoExiste() {
        when(avaliacaoRestauranteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            avaliacaoRestauranteService.editarAvaliacaoRestaurante(1L, avaliacaoRestaurante);
        });

        assertEquals("Avaliação de restaurante com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void editarAvaliacaoRestauranteDeveAtualizarAvaliacaoCorretamente() throws Exception {
        when(avaliacaoRestauranteRepository.findById(1L)).thenReturn(Optional.of(avaliacaoRestaurante));
        when(avaliacaoRestauranteRepository.save(any(AvaliacaoRestaurante.class))).thenReturn(avaliacaoRestaurante);

        AvaliacaoRestaurante updatedAvaliacao = new AvaliacaoRestaurante(1L, "Avaliação Atualizada", BigDecimal.valueOf(4.5), user, restaurante);
        AvaliacaoRestaurante savedAvaliacao = avaliacaoRestauranteService.editarAvaliacaoRestaurante(1L, updatedAvaliacao);

        assertNotNull(savedAvaliacao);
        assertEquals("Avaliação Atualizada", savedAvaliacao.getDescricao());
        assertEquals(BigDecimal.valueOf(4.5), savedAvaliacao.getNota());
    }

    @Test
    void somarNotasPorRestauranteDeveRetornarSomaCorretamente() {
        when(avaliacaoRestauranteRepository.somarNotasPorRestaurante(1L)).thenReturn(BigDecimal.valueOf(20.0));

        BigDecimal somaNotas = avaliacaoRestauranteService.somarNotasPorRestaurante(1L);

        assertEquals(BigDecimal.valueOf(20.0), somaNotas);
    }

    @Test
    void avaliarRestauranteDeveLancarExcecaoQuandoAvaliacaoJaExiste() {
        when(avaliacaoRestauranteRepository.findByUserIdAndRestauranteId(user.getId(), restaurante.getId())).thenReturn(Optional.of(avaliacaoRestaurante));

        AvaliacaoDuplicadaException exception = assertThrows(AvaliacaoDuplicadaException.class, () -> {
            avaliacaoRestauranteService.avaliarRestaurante(avaliacaoRestaurante);
        });

        assertEquals("O usuário já avaliou este restaurante.", exception.getMessage());
    }

    @Test
    void avaliarRestauranteDeveSalvarAvaliacaoCorretamente() {
        when(avaliacaoRestauranteRepository.findByUserIdAndRestauranteId(user.getId(), restaurante.getId())).thenReturn(Optional.empty());
        when(restauranteService.buscarPorId(restaurante.getId())).thenReturn(restaurante);
        when(avaliacaoRestauranteRepository.save(any(AvaliacaoRestaurante.class))).thenReturn(avaliacaoRestaurante);
        when(avaliacaoRestauranteRepository.somarNotasPorRestaurante(restaurante.getId())).thenReturn(BigDecimal.valueOf(24.0));
        when(restauranteService.atualizarRestaurante(any(Restaurante.class))).thenReturn(restaurante);

        AvaliacaoRestaurante savedAvaliacao = avaliacaoRestauranteService.avaliarRestaurante(avaliacaoRestaurante);

        assertNotNull(savedAvaliacao);
        assertEquals("Boa avaliação", savedAvaliacao.getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), savedAvaliacao.getNota());

        verify(restauranteService, times(1)).atualizarRestaurante(any(Restaurante.class));
    }
}
