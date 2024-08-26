package br.com.concurso.mappers;

import br.com.concurso.dtos.PratoCreateDTO;
import br.com.concurso.dtos.PratoResponseDTO;
import br.com.concurso.models.Prato;
import br.com.concurso.models.Restaurante;
import br.com.concurso.services.RestauranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class PratoMapperTest {

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private PratoMapper pratoMapper;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurante = new Restaurante(1L, "Restaurante Teste", "Rua Teste", BigDecimal.valueOf(4.5), 10, null, null);
    }

    @Test
    void toPratoDeveConverterPratoCreateDTOParaPrato() {
        when(restauranteService.buscarPorId(anyLong())).thenReturn(restaurante);

        PratoCreateDTO createDTO = new PratoCreateDTO("Prato Teste", "Descrição Teste", BigDecimal.valueOf(29.90), 1L, BigDecimal.valueOf(4.0), 10);

        Prato prato = pratoMapper.toPrato(createDTO);

        assertNotNull(prato);
        assertEquals("Prato Teste", prato.getNome());
        assertEquals("Descrição Teste", prato.getDescricao());
        assertEquals(BigDecimal.valueOf(29.90), prato.getPreco());
        assertEquals(1L, prato.getRestaurante().getId());
    }

    @Test
    void toDtoDeveConverterPratoParaPratoResponseDTO() {
        Prato prato = new Prato(1L, "Prato Teste", "Descrição Teste", BigDecimal.valueOf(29.90), BigDecimal.valueOf(4.5), 10, restaurante, null);

        PratoResponseDTO responseDTO = pratoMapper.toDto(prato);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Prato Teste", responseDTO.getNome());
        assertEquals("Descrição Teste", responseDTO.getDescricao());
        assertEquals(BigDecimal.valueOf(29.90), responseDTO.getPreco());
        assertEquals(1L, responseDTO.getRestauranteId());
        assertEquals(BigDecimal.valueOf(4.5), responseDTO.getNota());
        assertEquals(10, responseDTO.getQtdAvaliacoes());
    }

    @Test
    void toListDtoDeveConverterListaDePratosParaListaDePratoResponseDTO() {
        List<Prato> pratos = List.of(
                new Prato(1L, "Prato Teste 1", "Descrição Teste 1", BigDecimal.valueOf(29.90), BigDecimal.valueOf(4.5), 10, restaurante, null),
                new Prato(2L, "Prato Teste 2", "Descrição Teste 2", BigDecimal.valueOf(19.90), BigDecimal.valueOf(4.0), 8, restaurante, null)
        );

        List<PratoResponseDTO> dtos = pratoMapper.toListDto(pratos);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertEquals("Prato Teste 1", dtos.get(0).getNome());
        assertEquals("Descrição Teste 1", dtos.get(0).getDescricao());
        assertEquals(BigDecimal.valueOf(29.90), dtos.get(0).getPreco());
        assertEquals(1L, dtos.get(0).getRestauranteId());

        assertEquals(2L, dtos.get(1).getId());
        assertEquals("Prato Teste 2", dtos.get(1).getNome());
        assertEquals("Descrição Teste 2", dtos.get(1).getDescricao());
        assertEquals(BigDecimal.valueOf(19.90), dtos.get(1).getPreco());
        assertEquals(1L, dtos.get(1).getRestauranteId());
    }
}
