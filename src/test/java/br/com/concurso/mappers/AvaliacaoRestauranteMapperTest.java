package br.com.concurso.mappers;

import br.com.concurso.dtos.AvaliacaoRestauranteCreateDTO;
import br.com.concurso.dtos.AvaliacaoRestauranteResponseDTO;
import br.com.concurso.models.AvaliacaoRestaurante;
import br.com.concurso.models.Restaurante;
import br.com.concurso.models.User;
import br.com.concurso.services.RestauranteService;
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

class AvaliacaoRestauranteMapperTest {

    @Mock
    private UserService userService;

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private AvaliacaoRestauranteMapper avaliacaoRestauranteMapper;

    private User user;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "User Test", "user@test.com", "12345678901", null, null);
        restaurante = new Restaurante(1L, "Restaurante Test", "Rua Teste", BigDecimal.valueOf(4.5), 10, null, null);
    }

    @Test
    void toAvaliacaoRestauranteDeveConverterAvaliacaoRestauranteCreateDTOParaAvaliacaoRestaurante() {
        when(userService.buscarPorId(anyLong())).thenReturn(user);
        when(restauranteService.buscarPorId(anyLong())).thenReturn(restaurante);

        AvaliacaoRestauranteCreateDTO createDTO = new AvaliacaoRestauranteCreateDTO("Ótima comida", BigDecimal.valueOf(4.0), 1L, 1L);

        AvaliacaoRestaurante avaliacaoRestaurante = avaliacaoRestauranteMapper.toAvaliacaoRestaurante(createDTO);

        assertNotNull(avaliacaoRestaurante);
        assertEquals("Ótima comida", avaliacaoRestaurante.getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), avaliacaoRestaurante.getNota());
        assertEquals(1L, avaliacaoRestaurante.getUser().getId());
        assertEquals(1L, avaliacaoRestaurante.getRestaurante().getId());
    }

    @Test
    void toDtoDeveConverterAvaliacaoRestauranteParaAvaliacaoRestauranteResponseDTO() {
        AvaliacaoRestaurante avaliacaoRestaurante = new AvaliacaoRestaurante(1L, "Ótima comida", BigDecimal.valueOf(4.0), user, restaurante);

        AvaliacaoRestauranteResponseDTO responseDTO = avaliacaoRestauranteMapper.toDto(avaliacaoRestaurante);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Ótima comida", responseDTO.getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), responseDTO.getNota());
        assertEquals(1L, responseDTO.getUserId());
        assertEquals(1L, responseDTO.getRestauranteId());
    }

    @Test
    void toListDtoDeveConverterListaDeAvaliacoesRestaurantesParaListaDeAvaliacaoRestauranteResponseDTO() {
        List<AvaliacaoRestaurante> avaliacoes = List.of(
                new AvaliacaoRestaurante(1L, "Ótima comida", BigDecimal.valueOf(4.0), user, restaurante),
                new AvaliacaoRestaurante(2L, "Serviço excelente", BigDecimal.valueOf(5.0), user, restaurante)
        );

        List<AvaliacaoRestauranteResponseDTO> dtos = avaliacaoRestauranteMapper.toListDto(avaliacoes);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(1L, dtos.get(0).getId());
        assertEquals("Ótima comida", dtos.get(0).getDescricao());
        assertEquals(BigDecimal.valueOf(4.0), dtos.get(0).getNota());
        assertEquals(1L, dtos.get(0).getUserId());
        assertEquals(1L, dtos.get(0).getRestauranteId());

        assertEquals(2L, dtos.get(1).getId());
        assertEquals("Serviço excelente", dtos.get(1).getDescricao());
        assertEquals(BigDecimal.valueOf(5.0), dtos.get(1).getNota());
        assertEquals(1L, dtos.get(1).getUserId());
        assertEquals(1L, dtos.get(1).getRestauranteId());
    }
}
