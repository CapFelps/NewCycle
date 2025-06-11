// src/main/java/br/com/fiap/newcycle/api/mapper/TipoResiduoMapper.java
package br.com.fiap.newcycle.api.mapper;

import br.com.fiap.newcycle.api.dto.TipoResiduoDTO;
import br.com.fiap.newcycle.domain.model.TipoResiduo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TipoResiduoMapper {

    TipoResiduoDTO toDto(TipoResiduo entity);

    TipoResiduo toEntity(TipoResiduoDTO dto);

    void updateEntityFromDto(TipoResiduoDTO dto, @MappingTarget TipoResiduo entity);
}
