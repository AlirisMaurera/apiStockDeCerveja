package br.com.alirismaurera.apiStockDeCerveija.cerveijaDTOBuilder;

import br.com.alirismaurera.apiStockDeCerveija.dto.CervejaDTO;
import br.com.alirismaurera.apiStockDeCerveija.enums.TipoCerveja;
import lombok.Builder;

@Builder
public class CervejaDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "Bramma";

    @Builder.Default
    private String marca = "Ambev";

    @Builder.Default
    private Integer max = 50;

    @Builder.Default
    private Integer quantidade = 10;

    @Builder.Default
    private TipoCerveja tipo = TipoCerveja.LARGER;

    public CervejaDTO paraCervejaDTO(){
        return new CervejaDTO(id,nome, marca, max, quantidade, tipo);
    }
}
