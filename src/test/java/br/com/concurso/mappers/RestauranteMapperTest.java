package br.com.concurso.mappers;

import br.com.concurso.dtos.RestauranteCreateDTO;
import br.com.concurso.dtos.RestauranteResponseDTO;
import br.com.concurso.models.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteMapperTest {

    private RestauranteMapper restauranteMapper;

    @BeforeEach
    void setUp() {
        restauranteMapper = new RestauranteMapper();
    }

    @Test
    void toRestauranteDeveConverterRestauranteCreateDTOParaRestaurante() {
        RestauranteCreateDTO createDTO = new RestauranteCreateDTO("Restaurante Teste", "Rua Teste", BigDecimal.valueOf(4.5), 10);

        Restaurante restaurante = restauranteMapper.toRestaurante(createDTO);

        assertNotNull(restaurante);
        assertEquals("Restaurante Teste", restaurante.getNome());
        assertEquals("Rua Teste", restaurante.getEndereco());
        assertEquals(BigDecimal.valueOf(4.5), restaurante.getNota());
        assertEquals(10, restaurante.getQtdAvaliacoes());
    }

    @Test
    void toDtoDeveConverterRestauranteParaRestauranteResponseDTO() {
        Restaurante restaurante = new Restaurante(1L, "Restaurante Teste", "Rua Teste", BigDecimal.valueOf(4.5), 10, null, null);

        RestauranteResponseDTO responseDTO = restauranteMapper.toDto(restaurante);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Restaurante Teste", responseDTO.getNome());
        assertEquals("Rua Teste", responseDTO.getEndereco());
        assertEquals(BigDecimal.valueOf(4.5), responseDTO.getNota());
        assertEquals(10, responseDTO.getQtdAvaliacoes());
    }

    @Test
    void toListDtoDeveConverterListaDeRestaurantesParaListaDeRestauranteResponseDTO() {
        List<Restaurante> restaurantes = List.of(
                new Restaurante(1L, "Restaurante Teste 1", "Rua Teste 1", BigDecimal.valueOf(4.5), 10, null, null),
                new Restaurante(2L, "Restaurante Teste 2", "Rua Teste 2", BigDecimal.valueOf(4.0), 8, null, null)
        );

        List<RestauranteResponseDTO> dtos = restauranteMapper.toListDto(restaurantes);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertEquals("Restaurante Teste 1", dtos.get(0).getNome());
        assertEquals("Rua Teste 1", dtos.get(0).getEndereco());
        assertEquals(BigDecimal.valueOf(4.5), dtos.get(0).getNota());
        assertEquals(10, dtos.get(0).getQtdAvaliacoes());

        assertEquals(2L, dtos.get(1).getId());
        assertEquals("Restaurante Teste 2", dtos.get(1).getNome());
        assertEquals("Rua Teste 2", dtos.get(1).getEndereco());
        assertEquals(BigDecimal.valueOf(4.0), dtos.get(1).getNota());
        assertEquals(8, dtos.get(1).getQtdAvaliacoes());
    }
}
