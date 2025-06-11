// src/main/java/br/com/fiap/newcycle/api/mapper/ColetaMapper.java
package br.com.fiap.newcycle.api.mapper;

import br.com.fiap.newcycle.api.dto.ColetaDTO;
import br.com.fiap.newcycle.domain.model.Coleta;
import br.com.fiap.newcycle.domain.model.PontoColeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ColetaMapper {

    @Mapping(source = "pontoColeta.id", target = "pontoColetaId")
    ColetaDTO toDto(Coleta entity);

    @Mapping(source = "pontoColetaId", target = "pontoColeta")
    Coleta toEntity(ColetaDTO dto);

    @Mapping(source = "pontoColetaId", target = "pontoColeta")
    void updateEntityFromDto(ColetaDTO dto, @MappingTarget Coleta entity);

    default PontoColeta map(Long id) {
        if (id == null) return null;
        PontoColeta p = new PontoColeta();
        p.setId(id);
        return p;
    }
}
