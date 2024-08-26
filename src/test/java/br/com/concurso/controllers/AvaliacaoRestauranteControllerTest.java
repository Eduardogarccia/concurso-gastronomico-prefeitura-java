package br.com.concurso.controllers;

import br.com.concurso.dtos.AvaliacaoRestauranteCreateDTO;
import br.com.concurso.dtos.AvaliacaoRestauranteResponseDTO;
import br.com.concurso.mappers.AvaliacaoRestauranteMapper;
import br.com.concurso.models.AvaliacaoRestaurante;
import br.com.concurso.services.AvaliacaoRestauranteService;
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

class AvaliacaoRestauranteControllerTest {

    @Mock
    private AvaliacaoRestauranteService avaliacaoRestauranteService;

    @Mock
    private AvaliacaoRestauranteMapper avaliacaoRestauranteMapper;

    @InjectMocks
    private AvaliacaoRestauranteController avaliacaoRestauranteController;

    private AvaliacaoRestaurante avaliacaoRestaurante;
    private AvaliacaoRestauranteResponseDTO avaliacaoRestauranteResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        avaliacaoRestaurante = new AvaliacaoRestaurante(1L, "Ótima comida", BigDecimal.valueOf(4.5), null, null);
        avaliacaoRestauranteResponseDTO = new AvaliacaoRestauranteResponseDTO(1L, "Ótima comida", BigDecimal.valueOf(4.5), 1L, 1L);
    }

    @Test
    void avaliarRestauranteDeveRetornarStatusCreatedComAvaliacaoRestauranteResponseDTO() {
        AvaliacaoRestauranteCreateDTO createDTO = new AvaliacaoRestauranteCreateDTO("Ótima comida", BigDecimal.valueOf(4.5), 1L, 1L);

        when(avaliacaoRestauranteMapper.toAvaliacaoRestaurante(any(AvaliacaoRestauranteCreateDTO.class))).thenReturn(avaliacaoRestaurante);
        when(avaliacaoRestauranteService.avaliarRestaurante(any(AvaliacaoRestaurante.class))).thenReturn(avaliacaoRestaurante);
        when(avaliacaoRestauranteMapper.toDto(any(AvaliacaoRestaurante.class))).thenReturn(avaliacaoRestauranteResponseDTO);

        ResponseEntity<AvaliacaoRestauranteResponseDTO> response = avaliacaoRestauranteController.avaliarRestaurante(createDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(avaliacaoRestauranteResponseDTO, response.getBody());

        verify(avaliacaoRestauranteService, times(1)).avaliarRestaurante(any(AvaliacaoRestaurante.class));
        verify(avaliacaoRestauranteMapper, times(1)).toAvaliacaoRestaurante(any(AvaliacaoRestauranteCreateDTO.class));
        verify(avaliacaoRestauranteMapper, times(1)).toDto(any(AvaliacaoRestaurante.class));
    }

    @Test
    void obterTodosDeveRetornarStatusOkComListaDeAvaliacaoRestauranteResponseDTO() {
        List<AvaliacaoRestauranteResponseDTO> avaliacaoRestauranteResponseDTOList = List.of(avaliacaoRestauranteResponseDTO);

        when(avaliacaoRestauranteService.buscarTodos()).thenReturn(List.of(avaliacaoRestaurante));
        when(avaliacaoRestauranteMapper.toListDto(anyList())).thenReturn(avaliacaoRestauranteResponseDTOList);

        ResponseEntity<List<AvaliacaoRestauranteResponseDTO>> response = avaliacaoRestauranteController.obterTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoRestauranteResponseDTOList, response.getBody());

        verify(avaliacaoRestauranteService, times(1)).buscarTodos();
        verify(avaliacaoRestauranteMapper, times(1)).toListDto(anyList());
    }

    @Test
    void buscarPorIdDeveRetornarStatusOkComAvaliacaoRestauranteResponseDTO() {
        when(avaliacaoRestauranteService.buscarPorId(anyLong())).thenReturn(avaliacaoRestaurante);
        when(avaliacaoRestauranteMapper.toDto(any(AvaliacaoRestaurante.class))).thenReturn(avaliacaoRestauranteResponseDTO);

        ResponseEntity<AvaliacaoRestauranteResponseDTO> response = avaliacaoRestauranteController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoRestauranteResponseDTO, response.getBody());

        verify(avaliacaoRestauranteService, times(1)).buscarPorId(anyLong());
        verify(avaliacaoRestauranteMapper, times(1)).toDto(any(AvaliacaoRestaurante.class));
    }

    @Test
    void deletarPorIdDeveRetornarStatusOkComMensagem() {
        doNothing().when(avaliacaoRestauranteService).deletarPorId(anyLong());

        ResponseEntity<String> response = avaliacaoRestauranteController.deletarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Avaliação de restaurante com id 1 deletado", response.getBody());

        verify(avaliacaoRestauranteService, times(1)).deletarPorId(anyLong());
    }

    @Test
    void editarPorIdDeveRetornarStatusOkComAvaliacaoRestauranteResponseDTO() throws Exception {
        AvaliacaoRestauranteCreateDTO createDTO = new AvaliacaoRestauranteCreateDTO("Ótima comida", BigDecimal.valueOf(4.5), 1L, 1L);

        when(avaliacaoRestauranteMapper.toAvaliacaoRestaurante(any(AvaliacaoRestauranteCreateDTO.class))).thenReturn(avaliacaoRestaurante);
        when(avaliacaoRestauranteService.editarAvaliacaoRestaurante(anyLong(), any(AvaliacaoRestaurante.class))).thenReturn(avaliacaoRestaurante);
        when(avaliacaoRestauranteMapper.toDto(any(AvaliacaoRestaurante.class))).thenReturn(avaliacaoRestauranteResponseDTO);

        ResponseEntity<AvaliacaoRestauranteResponseDTO> response = avaliacaoRestauranteController.editarPorId(1L, createDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoRestauranteResponseDTO, response.getBody());

        verify(avaliacaoRestauranteService, times(1)).editarAvaliacaoRestaurante(anyLong(), any(AvaliacaoRestaurante.class));
        verify(avaliacaoRestauranteMapper, times(1)).toAvaliacaoRestaurante(any(AvaliacaoRestauranteCreateDTO.class));
        verify(avaliacaoRestauranteMapper, times(1)).toDto(any(AvaliacaoRestaurante.class));
    }
}
