// src/main/java/br/com/fiap/newcycle/api/mapper/AlertaMapper.java
package br.com.fiap.newcycle.api.mapper;

import br.com.fiap.newcycle.api.dto.AlertaDTO;
import br.com.fiap.newcycle.domain.model.Alerta;
import br.com.fiap.newcycle.domain.model.PontoColeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AlertaMapper {

    @Mapping(source = "pontoColeta.id", target = "pontoColetaId")
    AlertaDTO toDto(Alerta entity);

    @Mapping(source = "pontoColetaId", target = "pontoColeta")
    Alerta toEntity(AlertaDTO dto);

    @Mapping(source = "pontoColetaId", target = "pontoColeta")
    void updateEntityFromDto(AlertaDTO dto, @MappingTarget Alerta entity);

    default PontoColeta map(Long id) {
        if (id == null) return null;
        PontoColeta p = new PontoColeta();
        p.setId(id);
        return p;
    }
}
