// src/main/java/br/com/fiap/newcycle/api/mapper/PontoDestinoMapper.java
package br.com.fiap.newcycle.api.mapper;

import br.com.fiap.newcycle.api.dto.PontoDestinoDTO;
import br.com.fiap.newcycle.domain.model.PontoDestino;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PontoDestinoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "endereco", target = "endereco")
    PontoDestinoDTO toDto(PontoDestino entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "endereco", target = "endereco")
    PontoDestino toEntity(PontoDestinoDTO dto);

    void updateEntityFromDto(PontoDestinoDTO dto,
                             @MappingTarget PontoDestino entity);
}
