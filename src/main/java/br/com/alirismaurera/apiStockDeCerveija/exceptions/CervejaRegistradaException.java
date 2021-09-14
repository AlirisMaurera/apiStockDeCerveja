package br.com.alirismaurera.apiStockDeCerveija.exceptions;

import net.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CervejaRegistradaException extends Exception {

    public CervejaRegistradaException(String nome) {
        super(String.format("Cerveja com nome %s ja está registrada no sistema", nome));
    }
}
