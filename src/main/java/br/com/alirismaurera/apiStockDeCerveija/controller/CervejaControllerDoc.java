package br.com.alirismaurera.apiStockDeCerveija.controller;

import br.com.alirismaurera.apiStockDeCerveija.dto.CervejaDTO;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaNaoEncontradaException;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaRegistradaException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Cerveja stok")
public interface CervejaControllerDoc {

    @ApiOperation(value = "Operacao criacao cerveja")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creacao com sucesso"),
            @ApiResponse(code = 400, message = "Faltam campos obrigatorios ou valor de rango do campo incorreto")})
    CervejaDTO criarCerveja(CervejaDTO cervejaDTO) throws CervejaRegistradaException;

    @ApiOperation(value = "Devolve cerveja encontrada por un nome dado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cerveja encontrada com sucesso no sistema"),
            @ApiResponse(code = 404, message = "Cerveja nao encontrada")})
    CervejaDTO obterUmaCerveja(@PathVariable String nome) throws CervejaNaoEncontradaException;

    @ApiOperation(value = "Retorna una lista de todas as cervejas registradas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de todas as cerveja registradas no sistema"),
    })
    List<CervejaDTO> listarTodos();

    @ApiOperation(value = "Deleta uma cerveja encontrada dado uma id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Deletada com sucesso cerveja no sistema"),
            @ApiResponse(code = 404, message = "Cerveja con id dado nao encontrado")
    })
    void deletarCerveja(@PathVariable Long id) throws CervejaNaoEncontradaException;

}
