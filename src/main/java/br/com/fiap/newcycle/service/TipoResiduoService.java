package br.com.fiap.newcycle.service;

import br.com.fiap.newcycle.api.dto.TipoResiduoDTO;
import br.com.fiap.newcycle.api.mapper.TipoResiduoMapper;
import br.com.fiap.newcycle.domain.model.TipoResiduo;
import br.com.fiap.newcycle.domain.repository.TipoResiduoRepository;
import br.com.fiap.newcycle.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoResiduoService {

    private final TipoResiduoRepository repo;
    private final TipoResiduoMapper mapper;

    public TipoResiduoService(TipoResiduoRepository repo,
                              TipoResiduoMapper mapper) {
        this.repo   = repo;
        this.mapper = mapper;
    }

    @Transactional
    public TipoResiduoDTO criar(TipoResiduoDTO dto) {
        if (repo.findByNome(dto.nome()).isPresent()) {
            throw new DataIntegrityViolationException(
                    "Já existe um tipo de resíduo chamado '" + dto.nome() + "'");
        }

        TipoResiduo entity = mapper.toEntity(dto);
        entity = repo.save(entity);
        return mapper.toDto(entity);
    }

    public List<TipoResiduoDTO> listarTodos() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public TipoResiduoDTO buscarPorId(Long id) {
        TipoResiduo entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de resíduo não encontrado"));
        return mapper.toDto(entity);
    }

    @Transactional
    public TipoResiduoDTO atualizar(Long id, TipoResiduoDTO dto) {

        TipoResiduo entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de resíduo não encontrado"));

        if (dto.nome() != null &&
                !dto.nome().equalsIgnoreCase(entity.getNome()) &&
                repo.findByNome(dto.nome()).isPresent()) {

            throw new DataIntegrityViolationException(
                    "Já existe um tipo de resíduo chamado '" + dto.nome() + "'");
        }

        mapper.updateEntityFromDto(dto, entity);
        entity = repo.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void deletar(Long id) {

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de resíduo não encontrado");
        }
        repo.deleteById(id);
    }
}
