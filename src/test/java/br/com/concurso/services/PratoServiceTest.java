package br.com.concurso.services;

import br.com.concurso.exceptions.EntityNotFoundException;
import br.com.concurso.exceptions.RestauranteJaTemPratoException;
import br.com.concurso.models.Prato;
import br.com.concurso.models.Restaurante;
import br.com.concurso.repositories.PratoRepository;
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

class PratoServiceTest {

    @Mock
    private PratoRepository pratoRepository;

    @InjectMocks
    private PratoService pratoService;

    private Prato prato;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurante = new Restaurante(1L, "Restaurante Test", "Rua Teste", BigDecimal.valueOf(4.5), 10, null, null);
        prato = new Prato(1L, "Prato Test", "Descrição do prato", BigDecimal.valueOf(29.90), BigDecimal.valueOf(4.5), 10, restaurante, null);
    }

    @Test
    void salvarDeveLancarExcecaoQuandoRestauranteJaTemPrato() {
        when(pratoRepository.existsByRestauranteId(prato.getRestaurante().getId())).thenReturn(true);

        RestauranteJaTemPratoException exception = assertThrows(RestauranteJaTemPratoException.class, () -> {
            pratoService.salvar(prato);
        });

        assertEquals("Esse restaurante já tem um prato cadastrado!", exception.getMessage());
    }


    @Test
    void buscarPorIdDeveLancarExcecaoQuandoPratoNaoExiste() {
        when(pratoRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            pratoService.buscarPorId(1L);
        });

        assertEquals("Prato com id 1 não encontrado!", exception.getMessage());
    }

    @Test
    void buscarPorIdDeveRetornarPratoCorretamente() {
        when(pratoRepository.findById(1L)).thenReturn(Optional.of(prato));

        Prato foundPrato = pratoService.buscarPorId(1L);

        assertNotNull(foundPrato);
        assertEquals("Prato Test", foundPrato.getNome());
    }

    @Test
    void buscarTodosDeveRetornarListaDePratos() {
        List<Prato> pratos = List.of(
                new Prato(1L, "Prato Test 1", "Descrição do prato 1", BigDecimal.valueOf(29.90), BigDecimal.valueOf(4.5), 10, restaurante, null),
                new Prato(2L, "Prato Test 2", "Descrição do prato 2", BigDecimal.valueOf(19.90), BigDecimal.valueOf(4.0), 8, restaurante, null)
        );

        when(pratoRepository.findAll()).thenReturn(pratos);

        List<Prato> foundPratos = pratoService.buscarTodos();

        assertNotNull(foundPratos);
        assertEquals(2, foundPratos.size());
        assertEquals("Prato Test 1", foundPratos.get(0).getNome());
        assertEquals("Prato Test 2", foundPratos.get(1).getNome());
    }

    @Test
    void deletarPorIdDeveDeletarPratoCorretamente() {
        doNothing().when(pratoRepository).deleteById(1L);

        pratoService.deletarPorId(1L);

        verify(pratoRepository, times(1)).deleteById(1L);
    }

    @Test
    void editarPratoDeveLancarExcecaoQuandoPratoNaoExiste() {
        when(pratoRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            pratoService.editarPrato(1L, prato);
        });

        assertEquals("Prato com id 1 não encontrado!", exception.getMessage());
    }




    @Test
    void atualizarPratoDeveLancarExcecaoQuandoPratoNaoExiste() {
        when(pratoRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            pratoService.atualizarPrato(prato);
        });

        assertEquals("Prato com id 1 não encontrado.", exception.getMessage());
    }
}
