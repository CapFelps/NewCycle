package br.com.fiap.newcycle.api.mapper;

import br.com.fiap.newcycle.api.dto.ResiduoDTO;
import br.com.fiap.newcycle.domain.model.Coleta;
import br.com.fiap.newcycle.domain.model.PontoColeta;
import br.com.fiap.newcycle.domain.model.Residuo;
import br.com.fiap.newcycle.domain.model.TipoResiduo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResiduoMapper {

    @Mapping(source = "tipoResiduo.id", target = "tipoResiduoId")
    @Mapping(source = "pontoColeta.id", target = "pontoColetaId")
    @Mapping(source = "coleta.id", target = "coletaId")
    ResiduoDTO toDto(Residuo entity);

    @Mapping(source = "tipoResiduoId", target = "tipoResiduo")
    @Mapping(source = "pontoColetaId", target = "pontoColeta")
    @Mapping(source = "coletaId", target = "coleta")
    Residuo toEntity(ResiduoDTO dto);

    default TipoResiduo mapTipoResiduo(Long id) {
        if (id == null) {
            return null;
        }
        TipoResiduo tr = new TipoResiduo();
        tr.setId(id);
        return tr;
    }

    default PontoColeta mapPontoColeta(Long id) {
        if (id == null) {
            return null;
        }
        PontoColeta pc = new PontoColeta();
        pc.setId(id);
        return pc;
    }

    default Coleta mapColeta(Long id) {
        if (id == null) {
            return null;
        }
        Coleta c = new Coleta();
        c.setId(id);
        return c;
    }
}
