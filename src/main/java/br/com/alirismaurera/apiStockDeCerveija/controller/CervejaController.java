package br.com.alirismaurera.apiStockDeCerveija.controller;

import br.com.alirismaurera.apiStockDeCerveija.dto.CervejaDTO;
import br.com.alirismaurera.apiStockDeCerveija.dto.QuantidadeDTO;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaExcedeException;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaNaoEncontradaException;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaRegistradaException;
import br.com.alirismaurera.apiStockDeCerveija.service.CervejaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cervejas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CervejaController implements CervejaControllerDoc {


    private final CervejaService cervejaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CervejaDTO criarCerveja(@RequestBody @Valid CervejaDTO cervejaDTO) throws CervejaRegistradaException {
        return cervejaService.salvarCerveja(cervejaDTO);
    }

    @GetMapping("/{nome}")
    public CervejaDTO obterUmaCerveja(@PathVariable String nome) throws CervejaNaoEncontradaException {
        return cervejaService.obterUmaCerveja(nome);
    }

    @GetMapping
    public List<CervejaDTO> listarTodos(){
        return cervejaService.listarTodos();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCerveja(@PathVariable Long id) throws CervejaNaoEncontradaException {
        cervejaService.deletarCerveja(id);
    }

    @PatchMapping("/{id}/incrementar")
    public CervejaDTO incrementarQuantidade(@PathVariable Long id, @RequestBody @Valid QuantidadeDTO quantidadeDTO) throws CervejaNaoEncontradaException, CervejaExcedeException {
        return cervejaService.incrementar(id, quantidadeDTO.getQuantidade());
    }



}
