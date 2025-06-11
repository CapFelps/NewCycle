package br.com.fiap.newcycle.api.mapper;

import br.com.fiap.newcycle.api.dto.DestinacaoDTO;
import br.com.fiap.newcycle.domain.model.Destinacao;
import br.com.fiap.newcycle.domain.model.PontoDestino;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DestinacaoMapper {

    @Mapping(source = "pontoDestino.id", target = "pontoDestinoId")
    DestinacaoDTO toDto(Destinacao entity);

    @Mapping(source = "pontoDestinoId", target = "pontoDestino")
    Destinacao toEntity(DestinacaoDTO dto);

    @Mapping(source = "pontoDestinoId", target = "pontoDestino")
    void updateEntityFromDto(DestinacaoDTO dto,
                             @MappingTarget Destinacao entity);

    default PontoDestino map(Long id) {
        if (id == null) return null;
        PontoDestino pd = new PontoDestino();
        pd.setId(id);
        return pd;
    }
}
