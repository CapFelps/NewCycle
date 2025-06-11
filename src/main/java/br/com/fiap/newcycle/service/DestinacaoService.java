package br.com.fiap.newcycle.service;

import br.com.fiap.newcycle.api.dto.DestinacaoDTO;
import br.com.fiap.newcycle.api.mapper.DestinacaoMapper;
import br.com.fiap.newcycle.domain.model.Destinacao;
import br.com.fiap.newcycle.domain.model.PontoDestino;
import br.com.fiap.newcycle.domain.repository.DestinacaoRepository;
import br.com.fiap.newcycle.domain.repository.PontoDestinoRepository;
import br.com.fiap.newcycle.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Registra e gerencia o envio dos resíduos
 * para seus destinos finais (reciclagem, aterro, etc.).
 */
@Service
public class DestinacaoService {

    private final DestinacaoRepository repo;
    private final PontoDestinoRepository pontoRepo;
    private final DestinacaoMapper mapper;

    public DestinacaoService(DestinacaoRepository repo,
                             PontoDestinoRepository pontoRepo,
                             DestinacaoMapper mapper) {
        this.repo      = repo;
        this.pontoRepo = pontoRepo;
        this.mapper    = mapper;
    }

    @Transactional
    public DestinacaoDTO registrarEnvio(DestinacaoDTO dto) {
        PontoDestino pontoDestino = pontoRepo.findById(dto.pontoDestinoId())
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de destino não encontrado"));

        Destinacao dest = new Destinacao(
                dto.pesoKg(),
                pontoDestino
        );

        if (dto.dataRecebimento() != null) {
            dest.setDataRecebimento(dto.dataRecebimento());
        }

        dest = repo.save(dest);
        return mapper.toDto(dest);
    }

    public List<DestinacaoDTO> listarPorDestino(Long pontoDestinoId) {
        return repo.findByPontoDestino_Id(pontoDestinoId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public DestinacaoDTO buscarPorId(Long id) {
        Destinacao dest = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destinação não encontrada"));
        return mapper.toDto(dest);
    }

    @Transactional
    public DestinacaoDTO atualizar(Long id, DestinacaoDTO dto) {

        Destinacao dest = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destinação não encontrada"));

        if (dto.pontoDestinoId() != null &&
                !dto.pontoDestinoId().equals(dest.getPontoDestino().getId())) {

            PontoDestino novo = pontoRepo.findById(dto.pontoDestinoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ponto de destino não encontrado"));
            dest.setPontoDestino(novo);
        }

        mapper.updateEntityFromDto(dto, dest);

        if (dest.getDataRecebimento() == null && dto.dataRecebimento() != null) {
            dest.setDataRecebimento(dto.dataRecebimento());
        }

        if (dest.getDataEnvio() == null) {
            dest.setDataEnvio(LocalDate.now());
        }

        dest = repo.save(dest);
        return mapper.toDto(dest);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Destinação não encontrada");
        }
        repo.deleteById(id);
    }
}
