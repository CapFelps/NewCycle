package br.com.fiap.newcycle.service;

import br.com.fiap.newcycle.api.dto.ResiduoDTO;
import br.com.fiap.newcycle.api.mapper.ResiduoMapper;
import br.com.fiap.newcycle.domain.model.*;
import br.com.fiap.newcycle.domain.repository.*;
import br.com.fiap.newcycle.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResiduoService {

    private final ResiduoRepository     residuoRepo;
    private final PontoColetaRepository pontoRepo;
    private final TipoResiduoRepository tipoRepo;
    private final ColetaRepository      coletaRepo;
    private final ResiduoMapper         mapper;

    public ResiduoService(ResiduoRepository residuoRepo,
                          PontoColetaRepository pontoRepo,
                          TipoResiduoRepository tipoRepo,
                          ColetaRepository coletaRepo,
                          ResiduoMapper mapper) {

        this.residuoRepo = residuoRepo;
        this.pontoRepo   = pontoRepo;
        this.tipoRepo    = tipoRepo;
        this.coletaRepo  = coletaRepo;
        this.mapper      = mapper;
    }

    @Transactional
    public ResiduoDTO criar(ResiduoDTO dto) {

        PontoColeta ponto = pontoRepo.findById(dto.pontoColetaId())
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado"));

        TipoResiduo tipo = tipoRepo.findById(dto.tipoResiduoId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de resíduo não encontrado"));

        Residuo residuo = new Residuo(
                dto.descricao(),
                dto.pesoKg(),
                tipo,
                ponto
        );

        if (dto.coletaId() != null) {
            Coleta coleta = coletaRepo.findById(dto.coletaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Coleta não encontrada"));
            residuo.setColeta(coleta);
        }

        residuo = residuoRepo.save(residuo);

        ponto.setTotalResiduoKg(ponto.getTotalResiduoKg().add(dto.pesoKg()));
        pontoRepo.save(ponto);

        return mapper.toDto(residuo);
    }

    public List<ResiduoDTO> listarTodos() {
        return residuoRepo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ResiduoDTO> listarPorPonto(Long pontoId) {
        return residuoRepo.findByPontoColeta_Id(pontoId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ResiduoDTO buscarPorId(Long id) {
        Residuo res = residuoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resíduo não encontrado"));
        return mapper.toDto(res);
    }

    @Transactional
    public ResiduoDTO atualizar(Long id, ResiduoDTO dto) {

        Residuo res = residuoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resíduo não encontrado"));

        if (dto.pesoKg() != null && dto.pesoKg().compareTo(res.getPesoKg()) != 0) {

            BigDecimal delta = dto.pesoKg().subtract(res.getPesoKg());
            PontoColeta ponto = res.getPontoColeta();

            ponto.setTotalResiduoKg(ponto.getTotalResiduoKg().add(delta));
            pontoRepo.save(ponto);

            res.setPesoKg(dto.pesoKg());
        }

        if (dto.descricao() != null) {
            res.setDescricao(dto.descricao());
        }

        if (dto.tipoResiduoId() != null &&
                !dto.tipoResiduoId().equals(res.getTipoResiduo().getId())) {

            TipoResiduo novoTipo = tipoRepo.findById(dto.tipoResiduoId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Tipo de resíduo não encontrado"));
            res.setTipoResiduo(novoTipo);
        }

        if (dto.pontoColetaId() != null &&
                !dto.pontoColetaId().equals(res.getPontoColeta().getId())) {

            PontoColeta antigoPonto = res.getPontoColeta();
            PontoColeta novoPonto   = pontoRepo.findById(dto.pontoColetaId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Ponto de coleta não encontrado"));

            antigoPonto.setTotalResiduoKg(antigoPonto.getTotalResiduoKg()
                    .subtract(res.getPesoKg()));
            novoPonto.setTotalResiduoKg(novoPonto.getTotalResiduoKg()
                    .add(res.getPesoKg()));

            pontoRepo.save(antigoPonto);
            pontoRepo.save(novoPonto);

            res.setPontoColeta(novoPonto);
        }

        if (dto.coletaId() != null &&
                (res.getColeta() == null || !dto.coletaId().equals(res.getColeta().getId()))) {

            Coleta novaColeta = coletaRepo.findById(dto.coletaId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Coleta não encontrada"));
            res.setColeta(novaColeta);
        }

        Residuo salvo = residuoRepo.save(res);
        return mapper.toDto(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Residuo res = residuoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resíduo não encontrado"));

        PontoColeta ponto = res.getPontoColeta();
        ponto.setTotalResiduoKg(ponto.getTotalResiduoKg().subtract(res.getPesoKg()));
        pontoRepo.save(ponto);

        residuoRepo.delete(res);
    }
}
