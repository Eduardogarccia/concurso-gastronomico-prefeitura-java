package br.com.concurso.services;

import br.com.concurso.exceptions.AvaliacaoDuplicadaException;
import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.models.AvaliacaoPrato;
import br.com.concurso.models.Prato;
import br.com.concurso.models.User;
import br.com.concurso.repositories.AvaliacaoPratoRepository;
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

class AvaliacaoPratoServiceTest {

    @Mock
    private AvaliacaoPratoRepository avaliacaoPratoRepository;

    @Mock
    private PratoService pratoService;

    @InjectMocks
    private AvaliacaoPratoService avaliacaoPratoService;

    private AvaliacaoPrato avaliacaoPrato;
    private Prato prato;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "User Test", "user@test.com", "12345678901", null, null);
        prato = new Prato(1L, "Prato Test", "Descrição do prato", BigDecimal.valueOf(29.90), BigDecimal.valueOf(4.5), 10, null, null);
        avaliacaoPrato = new AvaliacaoPrato(1L, "Boa avaliação", BigDecimal.valueOf(4.0), user, prato);
    }

    @Test
    void salvarDeveSalvarAvaliacaoCorretamente() {
        when(avaliacaoPratoRepository.save(any(AvaliacaoPrato.class))).thenReturn(avaliacaoPrato);

        AvaliacaoPrato savedAvaliacao = avaliacaoPratoService.salvar(avaliacaoPrato);

        assertNotNull(savedAvaliacao);
        assertEquals("Boa avaliação", savedAvaliacao.getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), savedAvaliacao.getNota());
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoAvaliacaoNaoExiste() {
        when(avaliacaoPratoRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            avaliacaoPratoService.buscarPorId(1L);
        });

        assertEquals("Avaliação de prato com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void buscarPorIdDeveRetornarAvaliacaoCorretamente() {
        when(avaliacaoPratoRepository.findById(1L)).thenReturn(Optional.of(avaliacaoPrato));

        AvaliacaoPrato foundAvaliacao = avaliacaoPratoService.buscarPorId(1L);

        assertNotNull(foundAvaliacao);
        assertEquals("Boa avaliação", foundAvaliacao.getDescricao());
    }

    @Test
    void buscarTodosDeveRetornarListaDeAvaliacoes() {
        List<AvaliacaoPrato> avaliacoes = List.of(
                new AvaliacaoPrato(1L, "Avaliação 1", BigDecimal.valueOf(4.0), user, prato),
                new AvaliacaoPrato(2L, "Avaliação 2", BigDecimal.valueOf(5.0), user, prato)
        );

        when(avaliacaoPratoRepository.findAll()).thenReturn(avaliacoes);

        List<AvaliacaoPrato> foundAvaliacoes = avaliacaoPratoService.buscarTodos();

        assertNotNull(foundAvaliacoes);
        assertEquals(2, foundAvaliacoes.size());
        assertEquals("Avaliação 1", foundAvaliacoes.get(0).getDescricao());
        assertEquals("Avaliação 2", foundAvaliacoes.get(1).getDescricao());
    }

    @Test
    void deletarPorIdDeveDeletarAvaliacaoCorretamente() {
        doNothing().when(avaliacaoPratoRepository).deleteById(1L);

        avaliacaoPratoService.deletarPorId(1L);

        verify(avaliacaoPratoRepository, times(1)).deleteById(1L);
    }

    @Test
    void editarAvaliacaoPratoDeveLancarExcecaoQuandoAvaliacaoNaoExiste() {
        when(avaliacaoPratoRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            avaliacaoPratoService.editarAvaliacaoPrato(1L, avaliacaoPrato);
        });

        assertEquals("Avaliação de prato com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void editarAvaliacaoPratoDeveAtualizarAvaliacaoCorretamente() throws Exception {
        when(avaliacaoPratoRepository.findById(1L)).thenReturn(Optional.of(avaliacaoPrato));
        when(avaliacaoPratoRepository.save(any(AvaliacaoPrato.class))).thenReturn(avaliacaoPrato);

        AvaliacaoPrato updatedAvaliacao = new AvaliacaoPrato(1L, "Avaliação Atualizada", BigDecimal.valueOf(4.5), user, prato);
        AvaliacaoPrato savedAvaliacao = avaliacaoPratoService.editarAvaliacaoPrato(1L, updatedAvaliacao);

        assertNotNull(savedAvaliacao);
        assertEquals("Avaliação Atualizada", savedAvaliacao.getDescricao());
        assertEquals(BigDecimal.valueOf(4.5), savedAvaliacao.getNota());
    }

    @Test
    void somarNotasPorPratoDeveRetornarSomaCorretamente() {
        when(avaliacaoPratoRepository.somarNotasPorPrato(1L)).thenReturn(BigDecimal.valueOf(20.0));

        BigDecimal somaNotas = avaliacaoPratoService.somarNotasPorPrato(1L);

        assertEquals(BigDecimal.valueOf(20.0), somaNotas);
    }

    @Test
    void avaliarPratoDeveLancarExcecaoQuandoAvaliacaoJaExiste() {
        when(avaliacaoPratoRepository.findByUserIdAndPratoId(user.getId(), prato.getId())).thenReturn(Optional.of(avaliacaoPrato));

        AvaliacaoDuplicadaException exception = assertThrows(AvaliacaoDuplicadaException.class, () -> {
            avaliacaoPratoService.avaliarPrato(avaliacaoPrato);
        });

        assertEquals("O usuário já avaliou este prato.", exception.getMessage());
    }

    @Test
    void avaliarPratoDeveSalvarAvaliacaoCorretamente() {
        when(avaliacaoPratoRepository.findByUserIdAndPratoId(user.getId(), prato.getId())).thenReturn(Optional.empty());
        when(pratoService.buscarPorId(prato.getId())).thenReturn(prato);
        when(avaliacaoPratoRepository.save(any(AvaliacaoPrato.class))).thenReturn(avaliacaoPrato);
        when(avaliacaoPratoRepository.somarNotasPorPrato(prato.getId())).thenReturn(BigDecimal.valueOf(24.0));
        when(pratoService.atualizarPrato(any(Prato.class))).thenReturn(prato);

        AvaliacaoPrato savedAvaliacao = avaliacaoPratoService.avaliarPrato(avaliacaoPrato);

        assertNotNull(savedAvaliacao);
        assertEquals("Boa avaliação", savedAvaliacao.getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), savedAvaliacao.getNota());

        verify(pratoService, times(1)).atualizarPrato(any(Prato.class));
    }
}
