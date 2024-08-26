package br.com.concurso.controllers;

import br.com.concurso.dtos.AvaliacaoPratoCreateDTO;
import br.com.concurso.dtos.AvaliacaoPratoResponseDTO;
import br.com.concurso.mappers.AvaliacaoPratoMapper;
import br.com.concurso.models.AvaliacaoPrato;
import br.com.concurso.services.AvaliacaoPratoService;
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

class AvaliacaoPratoControllerTest {

    @Mock
    private AvaliacaoPratoService avaliacaoPratoService;

    @Mock
    private AvaliacaoPratoMapper avaliacaoPratoMapper;

    @InjectMocks
    private AvaliacaoPratoController avaliacaoPratoController;

    private AvaliacaoPrato avaliacaoPrato;
    private AvaliacaoPratoResponseDTO avaliacaoPratoResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        avaliacaoPrato = new AvaliacaoPrato(1L, "Excelente prato", BigDecimal.valueOf(4.5), null, null);
        avaliacaoPratoResponseDTO = new AvaliacaoPratoResponseDTO(1L, "Excelente prato", BigDecimal.valueOf(4.5), 1L, 1L);
    }

    @Test
    void avaliarPratoDeveRetornarStatusCreatedComAvaliacaoPratoResponseDTO() {
        AvaliacaoPratoCreateDTO createDTO = new AvaliacaoPratoCreateDTO("Excelente prato", BigDecimal.valueOf(4.5), 1L, 1L);

        when(avaliacaoPratoMapper.toAvaliacaoPrato(any(AvaliacaoPratoCreateDTO.class))).thenReturn(avaliacaoPrato);
        when(avaliacaoPratoService.avaliarPrato(any(AvaliacaoPrato.class))).thenReturn(avaliacaoPrato);
        when(avaliacaoPratoMapper.toDto(any(AvaliacaoPrato.class))).thenReturn(avaliacaoPratoResponseDTO);

        ResponseEntity<AvaliacaoPratoResponseDTO> response = avaliacaoPratoController.avaliarPrato(createDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(avaliacaoPratoResponseDTO, response.getBody());

        verify(avaliacaoPratoService, times(1)).avaliarPrato(any(AvaliacaoPrato.class));
        verify(avaliacaoPratoMapper, times(1)).toAvaliacaoPrato(any(AvaliacaoPratoCreateDTO.class));
        verify(avaliacaoPratoMapper, times(1)).toDto(any(AvaliacaoPrato.class));
    }

    @Test
    void obterTodosDeveRetornarStatusOkComListaDeAvaliacaoPratoResponseDTO() {
        List<AvaliacaoPratoResponseDTO> avaliacaoPratoResponseDTOList = List.of(avaliacaoPratoResponseDTO);

        when(avaliacaoPratoService.buscarTodos()).thenReturn(List.of(avaliacaoPrato));
        when(avaliacaoPratoMapper.toListDto(anyList())).thenReturn(avaliacaoPratoResponseDTOList);

        ResponseEntity<List<AvaliacaoPratoResponseDTO>> response = avaliacaoPratoController.obterTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoPratoResponseDTOList, response.getBody());

        verify(avaliacaoPratoService, times(1)).buscarTodos();
        verify(avaliacaoPratoMapper, times(1)).toListDto(anyList());
    }

    @Test
    void buscarPorIdDeveRetornarStatusOkComAvaliacaoPratoResponseDTO() {
        when(avaliacaoPratoService.buscarPorId(anyLong())).thenReturn(avaliacaoPrato);
        when(avaliacaoPratoMapper.toDto(any(AvaliacaoPrato.class))).thenReturn(avaliacaoPratoResponseDTO);

        ResponseEntity<AvaliacaoPratoResponseDTO> response = avaliacaoPratoController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacaoPratoResponseDTO, response.getBody());

        verify(avaliacaoPratoService, times(1)).buscarPorId(anyLong());
        verify(avaliacaoPratoMapper, times(1)).toDto(any(AvaliacaoPrato.class));
    }

    @Test
    void deletarPorIdDeveRetornarStatusOkComMensagem() {
        doNothing().when(avaliacaoPratoService).deletarPorId(anyLong());

        ResponseEntity<String> response = avaliacaoPratoController.deletarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Avaliação de prato com id 1 deletado", response.getBody());

        verify(avaliacaoPratoService, times(1)).deletarPorId(anyLong());
    }
}
