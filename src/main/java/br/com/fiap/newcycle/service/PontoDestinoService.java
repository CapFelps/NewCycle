package br.com.fiap.newcycle.service;

import br.com.fiap.newcycle.api.dto.PontoDestinoDTO;
import br.com.fiap.newcycle.api.mapper.PontoDestinoMapper;
import br.com.fiap.newcycle.domain.model.PontoDestino;
import br.com.fiap.newcycle.domain.repository.PontoDestinoRepository;
import br.com.fiap.newcycle.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PontoDestinoService {

    private final PontoDestinoRepository repo;
    private final PontoDestinoMapper mapper;

    public PontoDestinoService(PontoDestinoRepository repo,
                               PontoDestinoMapper mapper) {
        this.repo   = repo;
        this.mapper = mapper;
    }

    @Transactional
    public PontoDestinoDTO criar(PontoDestinoDTO dto) {

        // Nome deve ser único
        if (repo.findByNome(dto.nome()).isPresent()) {
            throw new DataIntegrityViolationException(
                    "Já existe um ponto de destino chamado '" + dto.nome() + "'");
        }

        PontoDestino entity = mapper.toEntity(dto);
        entity = repo.save(entity);
        return mapper.toDto(entity);
    }

    public List<PontoDestinoDTO> listarTodos() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public PontoDestinoDTO buscarPorId(Long id) {
        PontoDestino entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de destino não encontrado"));
        return mapper.toDto(entity);
    }

    @Transactional
    public PontoDestinoDTO atualizar(Long id, PontoDestinoDTO dto) {

        PontoDestino entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de destino não encontrado"));

        if (dto.nome() != null &&
                !dto.nome().equalsIgnoreCase(entity.getNome()) &&
                repo.findByNome(dto.nome()).isPresent()) {

            throw new DataIntegrityViolationException(
                    "Já existe um ponto de destino chamado '" + dto.nome() + "'");
        }

        mapper.updateEntityFromDto(dto, entity);
        entity = repo.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void deletar(Long id) {

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Ponto de destino não encontrado");
        }
        repo.deleteById(id);
    }
}
