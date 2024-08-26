package br.com.concurso.mappers;

import br.com.concurso.dtos.AvaliacaoPratoCreateDTO;
import br.com.concurso.dtos.AvaliacaoPratoResponseDTO;
import br.com.concurso.models.AvaliacaoPrato;
import br.com.concurso.models.Prato;
import br.com.concurso.models.User;
import br.com.concurso.services.PratoService;
import br.com.concurso.services.UserService;
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

class AvaliacaoPratoMapperTest {

    @Mock
    private UserService userService;

    @Mock
    private PratoService pratoService;

    @InjectMocks
    private AvaliacaoPratoMapper avaliacaoPratoMapper;

    private User user;
    private Prato prato;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "User Test", "user@test.com", "12345678901", null, null);
        prato = new Prato(1L, "Prato Test", "Descrição Teste", BigDecimal.valueOf(29.90), BigDecimal.valueOf(4.5), 10, null, null);
    }

    @Test
    void toAvaliacaoPratoDeveConverterAvaliacaoPratoCreateDTOParaAvaliacaoPrato() {
        when(userService.buscarPorId(anyLong())).thenReturn(user);
        when(pratoService.buscarPorId(anyLong())).thenReturn(prato);

        AvaliacaoPratoCreateDTO createDTO = new AvaliacaoPratoCreateDTO("Ótima comida", BigDecimal.valueOf(4.0), 1L, 1L);

        AvaliacaoPrato avaliacaoPrato = avaliacaoPratoMapper.toAvaliacaoPrato(createDTO);

        assertNotNull(avaliacaoPrato);
        assertEquals("Ótima comida", avaliacaoPrato.getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), avaliacaoPrato.getNota());
        assertEquals(1L, avaliacaoPrato.getUser().getId());
        assertEquals(1L, avaliacaoPrato.getPrato().getId());
    }

    @Test
    void toDtoDeveConverterAvaliacaoPratoParaAvaliacaoPratoResponseDTO() {
        AvaliacaoPrato avaliacaoPrato = new AvaliacaoPrato(1L, "Ótima comida", BigDecimal.valueOf(4.0), user, prato);

        AvaliacaoPratoResponseDTO responseDTO = avaliacaoPratoMapper.toDto(avaliacaoPrato);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Ótima comida", responseDTO.getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), responseDTO.getNota());
        assertEquals(1L, responseDTO.getUserId());
        assertEquals(1L, responseDTO.getPratoId());
    }

    @Test
    void toListDtoDeveConverterListaDeAvaliacoesPratosParaListaDeAvaliacaoPratoResponseDTO() {
        List<AvaliacaoPrato> avaliacoes = List.of(
                new AvaliacaoPrato(1L, "Ótima comida", BigDecimal.valueOf(4.0), user, prato),
                new AvaliacaoPrato(2L, "Sabor excelente", BigDecimal.valueOf(5.0), user, prato)
        );

        List<AvaliacaoPratoResponseDTO> dtos = avaliacaoPratoMapper.toListDto(avaliacoes);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(1L, dtos.get(0).getId());
        assertEquals("Ótima comida", dtos.get(0).getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), dtos.get(0).getNota());
        assertEquals(1L, dtos.get(0).getUserId());
        assertEquals(1L, dtos.get(0).getPratoId());

        assertEquals(2L, dtos.get(1).getId());
        assertEquals("Sabor excelente", dtos.get(1).getDescricao());
        assertEquals(BigDecimal.valueOf(5.0), dtos.get(1).getNota());
        assertEquals(1L, dtos.get(1).getUserId());
        assertEquals(1L, dtos.get(1).getPratoId());
    }
}
