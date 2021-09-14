package br.com.alirismaurera.apiStockDeCerveija.cerveijaControllerTest;

import br.com.alirismaurera.apiStockDeCerveija.cerveijaDTOBuilder.CervejaDTOBuilder;
import br.com.alirismaurera.apiStockDeCerveija.controller.CervejaController;
import br.com.alirismaurera.apiStockDeCerveija.dto.CervejaDTO;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaNaoEncontradaException;
import br.com.alirismaurera.apiStockDeCerveija.service.CervejaService;
import br.com.alirismaurera.apiStockDeCerveija.utils.ConvertirEmJsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CervejaControllerTest {

    private static final String URL_CERVEJA_API_PATH = "/api/v1/cerveijas";
    private static final Long CERVEJA_ID_VALIDA = 1L;
    private static final Long CERVEJA_ID_INVALIDA = 2L;
    private static final String URL_CERVEJA_API_INCREMENTO = "/incrementar";
    private static final String URL_CERVEJA_API_DECREMENTO = "/decrementar";

    private MockMvc mockMvc;

    @Mock
    private CervejaService cervejaService;

    @InjectMocks
    private CervejaController cervejaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cervejaController)
                .setCustomArgumentResolvers((new PageableHandlerMethodArgumentResolver()))
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void cuandoPostEChamado_EntaoUmaCervejaECriada() throws Exception {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();

        //when
        when(cervejaService.salvarCerveja(cervejaDTO)).thenReturn(cervejaDTO);

        //then
        mockMvc.perform(post(URL_CERVEJA_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ConvertirEmJsonUtils.comoJson(cervejaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(cervejaDTO.getNome())))
                .andExpect(jsonPath("$.marca", is(cervejaDTO.getMarca())))
                .andExpect(jsonPath("$.tipo", is(cervejaDTO.getTipo().toString())));
    }

    @Test
    void cuandoPostEChamadoFaltandoUmCampo_EntaoDaUmaExcepcao() throws Exception {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        cervejaDTO.setMarca(null);


        //then
        mockMvc.perform(post(URL_CERVEJA_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ConvertirEmJsonUtils.comoJson(cervejaDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cuandoGetEChamadoComUmDeterminadoNome_EntaoEleRetornaACervejaeStatusCreated() throws Exception {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();

        when(cervejaService.obterUmaCerveja(cervejaDTO.getNome())).thenReturn(cervejaDTO);

        mockMvc.perform(get(URL_CERVEJA_API_PATH + "/" + cervejaDTO.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(cervejaDTO.getNome())))
                .andExpect(jsonPath("$.marca", is(cervejaDTO.getMarca())))
                .andExpect(jsonPath("$.tipo", is(cervejaDTO.getTipo().toString())));
    }

    @Test
    void cuandoGetEChamadoComUmDeterminadoNome_EntaoEleRetornaNaoEncontrado() throws Exception {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();

        when(cervejaService.obterUmaCerveja(cervejaDTO.getNome())).thenThrow(CervejaNaoEncontradaException.class);

        mockMvc.perform(get(URL_CERVEJA_API_PATH + "/" + cervejaDTO.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void cuandoGetEChamadoParaListarAsCervejas_EntaoEleRetornaUmaLista() throws Exception {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();

        when(cervejaService.listarTodos()).thenReturn(Collections.singletonList(cervejaDTO));

        mockMvc.perform(get(URL_CERVEJA_API_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is(cervejaDTO.getNome())))
                .andExpect(jsonPath("$[0].marca", is(cervejaDTO.getMarca())))
                .andExpect(jsonPath("$[0].tipo", is(cervejaDTO.getTipo().toString())));
    }

    @Test
    void cuandoDeleteEChamadoComUmDeterminadoID_EntaoEleDeletaACervejaEmostraStatusNoConten() throws Exception {
        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();

       doNothing().when(cervejaService).deletarCerveja(cervejaDTO.getId());

        mockMvc.perform(delete(URL_CERVEJA_API_PATH + "/" + cervejaDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void cuandoDeleteEChamadoComUmDeterminadoIDInvalido_EntaoElemostraStatusNoFound() throws Exception {

        doThrow(CervejaNaoEncontradaException.class).when(cervejaService).deletarCerveja(CERVEJA_ID_INVALIDA);

        mockMvc.perform(delete(URL_CERVEJA_API_PATH + "/" + CERVEJA_ID_INVALIDA)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}