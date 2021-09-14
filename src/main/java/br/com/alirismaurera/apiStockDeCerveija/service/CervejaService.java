package br.com.alirismaurera.apiStockDeCerveija.service;

import br.com.alirismaurera.apiStockDeCerveija.dto.CervejaDTO;
import br.com.alirismaurera.apiStockDeCerveija.entity.Cerveja;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaExcedeException;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaNaoEncontradaException;
import br.com.alirismaurera.apiStockDeCerveija.exceptions.CervejaRegistradaException;
import br.com.alirismaurera.apiStockDeCerveija.mapper.CervejaMapper;
import br.com.alirismaurera.apiStockDeCerveija.repository.CervejaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CervejaService {



    private final CervejaRepository cervejaRepository;

    private final CervejaMapper cervejaMapper = CervejaMapper.INSTANCE;


    public CervejaDTO salvarCerveja(CervejaDTO cervejaDTO) throws CervejaRegistradaException {
        verificarSeEstaRegistrado(cervejaDTO.getNome());
        Cerveja cerveja = cervejaMapper.toModel(cervejaDTO);
        Cerveja cervejaSalva = cervejaRepository.save(cerveja);
        return cervejaMapper.toDTO(cervejaSalva);

    }

    public CervejaDTO obterUmaCerveja(String nome) throws CervejaNaoEncontradaException {
        Cerveja cerveja = cervejaRepository.findByNome(nome)
                .orElseThrow(() -> new CervejaNaoEncontradaException(nome));
        return cervejaMapper.toDTO(cerveja);
    }

    public List<CervejaDTO> listarTodos(){
        return cervejaRepository.findAll()
                .stream()
                .map(cervejaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deletarCerveja(Long id) throws CervejaNaoEncontradaException {
        verificarSeExiste(id);
        cervejaRepository.deleteById(id);
    }

    private void verificarSeEstaRegistrado(String nome) throws CervejaRegistradaException {
        Optional<Cerveja> cervejaOptional = cervejaRepository.findByNome(nome);
        if(cervejaOptional.isPresent()){throw new CervejaRegistradaException(nome);}
    }

    private Cerveja verificarSeExiste(Long id) throws CervejaNaoEncontradaException {
        return cervejaRepository.findById(id).orElseThrow(() -> new CervejaNaoEncontradaException(id));
    }

    public CervejaDTO incrementar(Long id, int quantidadeincrementada) throws CervejaNaoEncontradaException, CervejaExcedeException {
        Cerveja cervejaIncrementada = verificarSeExiste(id);
        int quantidadeTotal = quantidadeincrementada + cervejaIncrementada.getQuantidade();
        if(quantidadeTotal<= cervejaIncrementada.getMax()){
            cervejaIncrementada.setQuantidade(quantidadeTotal);
           Cerveja cervejaNova = cervejaRepository.save(cervejaIncrementada);
           return cervejaMapper.toDTO(cervejaNova);
        }
        throw new CervejaExcedeException(id, quantidadeincrementada);
    }

    }
