package br.com.alirismaurera.apiStockDeCerveija.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CervejaExcedeException extends Exception{

    public CervejaExcedeException(Long id, int quantidade) {
        super(String.format("cerveja $s ID informado para incrementar excede a " +
                "capacidade maxima de stock: %s", id, quantidade));
    }
}
