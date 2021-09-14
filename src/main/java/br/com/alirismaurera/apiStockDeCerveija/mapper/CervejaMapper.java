package br.com.alirismaurera.apiStockDeCerveija.mapper;

import br.com.alirismaurera.apiStockDeCerveija.dto.CervejaDTO;
import br.com.alirismaurera.apiStockDeCerveija.entity.Cerveja;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CervejaMapper {

    CervejaMapper INSTANCE = Mappers.getMapper(CervejaMapper.class);

    Cerveja toModel(CervejaDTO cervejaDTO);

    CervejaDTO toDTO(Cerveja cerveja);
}
