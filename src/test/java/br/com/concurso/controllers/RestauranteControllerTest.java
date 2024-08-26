package br.com.concurso.controllers;

import br.com.concurso.dtos.RestauranteCreateDTO;
import br.com.concurso.dtos.RestauranteResponseDTO;
import br.com.concurso.mappers.RestauranteMapper;
import br.com.concurso.models.Restaurante;
import br.com.concurso.services.RestauranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RestauranteControllerTest {

    @Mock
    private RestauranteService restauranteService;

    @Mock
    private RestauranteMapper restauranteMapper;

    @InjectMocks
    private RestauranteController restauranteController;

    private Restaurante restaurante;
    private RestauranteResponseDTO restauranteResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurante = new Restaurante(1L, "Restaurante Teste", "Rua Teste", BigDecimal.valueOf(4.5), 10, null, null);
        restauranteResponseDTO = new RestauranteResponseDTO(1L, "Restaurante Teste", "Rua Teste", BigDecimal.valueOf(4.5), 10);
    }

    @Test
    void salvarDeveRetornarStatusCreatedComRestauranteResponseDTO() {
        RestauranteCreateDTO createDTO = new RestauranteCreateDTO("Restaurante Teste", "Rua Teste", BigDecimal.valueOf(4.5), 10);

        when(restauranteMapper.toRestaurante(any(RestauranteCreateDTO.class))).thenReturn(restaurante);
        when(restauranteService.salvar(any(Restaurante.class))).thenReturn(restaurante);
        when(restauranteMapper.toDto(any(Restaurante.class))).thenReturn(restauranteResponseDTO);

        ResponseEntity<RestauranteResponseDTO> response = restauranteController.salvar(createDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restauranteResponseDTO, response.getBody());

        verify(restauranteService, times(1)).salvar(any(Restaurante.class));
        verify(restauranteMapper, times(1)).toRestaurante(any(RestauranteCreateDTO.class));
        verify(restauranteMapper, times(1)).toDto(any(Restaurante.class));
    }

    @Test
    void obterTodosDeveRetornarStatusOkComListaDeRestauranteResponseDTO() {
        List<RestauranteResponseDTO> restauranteResponseDTOList = List.of(restauranteResponseDTO);

        when(restauranteService.buscarTodos()).thenReturn(List.of(restaurante));
        when(restauranteMapper.toListDto(anyList())).thenReturn(restauranteResponseDTOList);

        ResponseEntity<List<RestauranteResponseDTO>> response = restauranteController.obterTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restauranteResponseDTOList, response.getBody());

        verify(restauranteService, times(1)).buscarTodos();
        verify(restauranteMapper, times(1)).toListDto(anyList());
    }

    @Test
    void buscarPorIdDeveRetornarStatusOkComRestauranteResponseDTO() {
        when(restauranteService.buscarPorId(anyLong())).thenReturn(restaurante);
        when(restauranteMapper.toDto(any(Restaurante.class))).thenReturn(restauranteResponseDTO);

        ResponseEntity<RestauranteResponseDTO> response = restauranteController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restauranteResponseDTO, response.getBody());

        verify(restauranteService, times(1)).buscarPorId(anyLong());
        verify(restauranteMapper, times(1)).toDto(any(Restaurante.class));
    }

    @Test
    void deletarPorIdDeveRetornarStatusOkComMensagem() {
        doNothing().when(restauranteService).deletarPorId(anyLong());

        ResponseEntity<String> response = restauranteController.deletarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurante com id 1 deletado", response.getBody());

        verify(restauranteService, times(1)).deletarPorId(anyLong());
    }
}
