package br.com.concurso.controllers;

import br.com.concurso.dtos.PratoCreateDTO;
import br.com.concurso.dtos.PratoResponseDTO;
import br.com.concurso.mappers.PratoMapper;
import br.com.concurso.models.Prato;
import br.com.concurso.services.PratoService;
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

class PratoControllerTest {

    @Mock
    private PratoService pratoService;

    @Mock
    private PratoMapper pratoMapper;

    @InjectMocks
    private PratoController pratoController;

    private Prato prato;
    private PratoResponseDTO pratoResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        prato = new Prato(1L, "Prato Teste", "Descrição Teste", BigDecimal.valueOf(29.90), BigDecimal.valueOf(4.5), 10, null, null);
        pratoResponseDTO = new PratoResponseDTO(1L, "Prato Teste", "Descrição Teste", BigDecimal.valueOf(29.90), 10, BigDecimal.valueOf(4.5), 1L);
    }

    @Test
    void salvarDeveRetornarStatusCreatedComPratoResponseDTO() {
        PratoCreateDTO createDTO = new PratoCreateDTO("Prato Teste", "Descrição Teste", BigDecimal.valueOf(29.90), 1L, BigDecimal.valueOf(4.5), 10);

        when(pratoMapper.toPrato(any(PratoCreateDTO.class))).thenReturn(prato);
        when(pratoService.salvar(any(Prato.class))).thenReturn(prato);
        when(pratoMapper.toDto(any(Prato.class))).thenReturn(pratoResponseDTO);

        ResponseEntity<PratoResponseDTO> response = pratoController.salvar(createDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pratoResponseDTO, response.getBody());

        verify(pratoService, times(1)).salvar(any(Prato.class));
        verify(pratoMapper, times(1)).toPrato(any(PratoCreateDTO.class));
        verify(pratoMapper, times(1)).toDto(any(Prato.class));
    }

    @Test
    void obterTodosDeveRetornarStatusOkComListaDePratoResponseDTO() {
        List<PratoResponseDTO> pratoResponseDTOList = List.of(pratoResponseDTO);

        when(pratoService.buscarTodos()).thenReturn(List.of(prato));
        when(pratoMapper.toListDto(anyList())).thenReturn(pratoResponseDTOList);

        ResponseEntity<List<PratoResponseDTO>> response = pratoController.obterTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pratoResponseDTOList, response.getBody());

        verify(pratoService, times(1)).buscarTodos();
        verify(pratoMapper, times(1)).toListDto(anyList());
    }

    @Test
    void buscarPorIdDeveRetornarStatusOkComPratoResponseDTO() {
        when(pratoService.buscarPorId(anyLong())).thenReturn(prato);
        when(pratoMapper.toDto(any(Prato.class))).thenReturn(pratoResponseDTO);

        ResponseEntity<PratoResponseDTO> response = pratoController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pratoResponseDTO, response.getBody());

        verify(pratoService, times(1)).buscarPorId(anyLong());
        verify(pratoMapper, times(1)).toDto(any(Prato.class));
    }

    @Test
    void deletarPorIdDeveRetornarStatusOkComMensagem() {
        doNothing().when(pratoService).deletarPorId(anyLong());

        ResponseEntity<String> response = pratoController.deletarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Prato com id 1 deletado", response.getBody());

        verify(pratoService, times(1)).deletarPorId(anyLong());
    }
}
