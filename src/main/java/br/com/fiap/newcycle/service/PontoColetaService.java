package br.com.fiap.newcycle.service;

import br.com.fiap.newcycle.api.dto.PontoColetaDTO;
import br.com.fiap.newcycle.api.mapper.PontoColetaMapper;
import br.com.fiap.newcycle.domain.model.PontoColeta;
import br.com.fiap.newcycle.domain.repository.PontoColetaRepository;
import br.com.fiap.newcycle.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PontoColetaService {

    private final PontoColetaRepository repository;
    private final PontoColetaMapper mapper;

    public PontoColetaService(PontoColetaRepository repository, PontoColetaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public PontoColetaDTO criar(PontoColetaDTO dto) {
        PontoColeta entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public PontoColetaDTO atualizar(Long id, PontoColetaDTO dto) {
        PontoColeta entity = buscarEntidade(id);
        mapper.updateEntityFromDto(dto, entity); // copia somente campos mapeados
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public PontoColetaDTO buscarPorId(Long id) {
        return mapper.toDto(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<PontoColetaDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ponto de coleta não encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private PontoColeta buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado: " + id));
    }
}
