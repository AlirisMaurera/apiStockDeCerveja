package br.com.alirismaurera.apiStockDeCerveija.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoCerveja {

    LARGER("Larger"),
    MALZBIER("Malzbier"),
    WITBIER("Witbier"),
    WEISS("Weiss"),
    ALE("Ale"),
    IPA("Ipa"),
    STOUT("Stout");

    private String descricao;
}
