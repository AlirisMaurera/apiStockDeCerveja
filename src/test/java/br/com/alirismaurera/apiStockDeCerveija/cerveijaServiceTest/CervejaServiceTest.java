package br.com.alirismaurera.apiStockDeCerveija.cerveijaServiceTest;

import br.com.alirismaurera.apiStockDeCerveija.cerveijaDTOBuilder.CervejaDTOBuilder;
import br.com.alirismaurera.apiStockDeCerveija.dto.CervejaDTO;
import br.com.alirismaurera.apiStockDeCerveija.entity.Cerveja;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaExcedeException;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaNaoEncontradaException;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaRegistradaException;
import br.com.alirismaurera.apiStockDeCerveija.mapper.CervejaMapper;
import br.com.alirismaurera.apiStockDeCerveija.repository.CervejaRepository;
import br.com.alirismaurera.apiStockDeCerveija.service.CervejaService;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CervejaServiceTest {

    @Mock
    private CervejaRepository cervejaRepository;

    private CervejaMapper cervejaMapper = CervejaMapper.INSTANCE;

    @InjectMocks
    private CervejaService cervejaService;

    @Test
    void quandoUmaCervejaEInformada_EntaoElaDeberiaSerCriada() throws CervejaRegistradaException {

        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaSalvadaEsperada = cervejaMapper.toModel(cervejaDTO);

        //when
        when(cervejaRepository.findByNome(cervejaDTO.getNome())).thenReturn(Optional.empty());
        when(cervejaRepository.save(cervejaSalvadaEsperada)).thenReturn(cervejaSalvadaEsperada);

        //then
        CervejaDTO cervejaDTOCriada = cervejaService.salvarCerveja(cervejaDTO);

        assertThat(cervejaDTOCriada.getId(), is(equalTo(cervejaDTO.getId())));
        assertThat(cervejaDTOCriada.getNome(), is(equalTo(cervejaDTO.getNome())));
        assertThat(cervejaDTOCriada.getQuantidade(), is(equalTo(cervejaDTO.getQuantidade())));
        assertThat(cervejaDTOCriada.getQuantidade(), is(greaterThan(2)));


    }

    @Test
    void cuandoUmaCervejaInformadaJaCriada_EntaoDeveriaLanzarUmaExcepcao() {

        //given
        CervejaDTO cervejaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaSDuplicada = cervejaMapper.toModel(cervejaDTO);

        //when
        when(cervejaRepository.findByNome(cervejaDTO.getNome())).thenReturn(Optional.of(cervejaSDuplicada));

        //then
        assertThrows(CervejaRegistradaException.class, () -> cervejaService.salvarCerveja(cervejaDTO));
    }

    @Test
    void cuandoUmaCervejaEInformada_EntaoRetornaACerveija() throws CervejaNaoEncontradaException {
        CervejaDTO cervejaEncontradaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEncontrada = cervejaMapper.toModel(cervejaEncontradaDTO);

        //when
        when(cervejaRepository.findByNome(cervejaEncontrada.getNome())).thenReturn(Optional.of(cervejaEncontrada));

        //then
        CervejaDTO cervejaDTO = cervejaService.obterUmaCerveja(cervejaEncontradaDTO.getNome());

        assertThat(cervejaDTO, is(equalTo(cervejaEncontradaDTO)));
    }

    @Test
    void cuandoUmaCervejaENaoEstaRegistrada_EntaoRetornaUmaExcepcao() throws CervejaNaoEncontradaException {
        //give
        CervejaDTO cervejaNaoEncontradaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();


        //when
        when(cervejaRepository.findByNome(cervejaNaoEncontradaDTO.getNome())).thenReturn(Optional.empty());

        //then
        assertThrows(CervejaNaoEncontradaException.class, () -> cervejaService.obterUmaCerveja(cervejaNaoEncontradaDTO.getNome()));

    }

    @Test
    void cuandoGetEChamdoParaListarTodasAsCervejas_EntaoRetornaLista() {
        //give
        CervejaDTO cervejaEncontradaDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEncontrada = cervejaMapper.toModel(cervejaEncontradaDTO);

        //when
        when(cervejaRepository.findAll()).thenReturn(Collections.singletonList(cervejaEncontrada));

        //then
        List<CervejaDTO> listaDeCerveja = cervejaService.listarTodos();

        assertThat(listaDeCerveja, is(not(empty())));
        assertThat(listaDeCerveja.get(0), is(equalTo((cervejaEncontradaDTO))));

    }

    @Test
    void cuandoGetEChamdoParaListarTodasAsCervejas_EntaoRetornaListaVazia() {

        //when
        when(cervejaRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<CervejaDTO> listaDeCerveja = cervejaService.listarTodos();

        assertThat(listaDeCerveja, is(empty()));

    }

    @Test
    void cuandoDaroNomeDeUmaCerveja_EntaoDeletaEla() throws CervejaNaoEncontradaException {
        //give
        CervejaDTO cervejaDeletarDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaEDeletar = cervejaMapper.toModel(cervejaDeletarDTO);
        //when
        when(cervejaRepository.findById(cervejaDeletarDTO.getId())).thenReturn(Optional.of(cervejaEDeletar));
        doNothing().when(cervejaRepository).deleteById(cervejaDeletarDTO.getId());

        //then
        cervejaService.deletarCerveja(cervejaDeletarDTO.getId());

        verify(cervejaRepository, times(1)).findById(cervejaDeletarDTO.getId());
        verify(cervejaRepository, times(1)).deleteById(cervejaDeletarDTO.getId());

    }

    @Test
    void cuandoEChamadoIncrementar_ThenEIncrementadoOStockDeCerveja() throws CervejaNaoEncontradaException, CervejaExcedeException {
        //give
        CervejaDTO cervejaIncrementarDTO = CervejaDTOBuilder.builder().build().paraCervejaDTO();
        Cerveja cervejaIncrementar = cervejaMapper.toModel(cervejaIncrementarDTO);
        //when
        when(cervejaRepository.findById(cervejaIncrementarDTO.getId())).thenReturn(Optional.of(cervejaIncrementar));
        when(cervejaRepository.save(cervejaIncrementar)).thenReturn(cervejaIncrementar);

        int quantidadeParaIncrementar = 10;
        int quantidadeEsperadaTotal = cervejaIncrementar.getQuantidade() + quantidadeParaIncrementar;
        CervejaDTO cerveijaDTO = cervejaService.incrementar(cervejaIncrementarDTO.getId(), quantidadeParaIncrementar);

        assertThat(quantidadeEsperadaTotal, equalTo((cerveijaDTO.getQuantidade())));
        assertThat(quantidadeEsperadaTotal, lessThan(cerveijaDTO.getMax()));

    }
}