package br.com.alirismaurera.apiStockDeCerveija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CervejaNaoEncontradaException extends Exception{

    public CervejaNaoEncontradaException(Long id) {
        super(String.format("Cerveja com id %s nao encontrada no sistema", id));
    }

    public CervejaNaoEncontradaException(String nomeCerveja) {
        super(String.format("Cerveja com nome %s nao encontrada no sistema", nomeCerveja));
    }
}
