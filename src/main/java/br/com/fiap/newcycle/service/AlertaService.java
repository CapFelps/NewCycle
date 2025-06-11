package br.com.fiap.newcycle.service;

import br.com.fiap.newcycle.api.dto.AlertaDTO;
import br.com.fiap.newcycle.api.mapper.AlertaMapper;
import br.com.fiap.newcycle.domain.model.Alerta;
import br.com.fiap.newcycle.domain.model.PontoColeta;
import br.com.fiap.newcycle.domain.repository.AlertaRepository;
import br.com.fiap.newcycle.domain.repository.PontoColetaRepository;
import br.com.fiap.newcycle.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepo;
    private final PontoColetaRepository pontoRepo;
    private final AlertaMapper mapper;

    public AlertaService(AlertaRepository alertaRepo,
                         PontoColetaRepository pontoRepo,
                         AlertaMapper mapper) {
        this.alertaRepo = alertaRepo;
        this.pontoRepo  = pontoRepo;
        this.mapper     = mapper;
    }

    @Transactional
    public AlertaDTO criarManualmente(Long pontoColetaId, String mensagem) {

        PontoColeta ponto = pontoRepo.findById(pontoColetaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado"));

        Alerta alerta = new Alerta(mensagem, LocalDateTime.now(), ponto);
        alerta = alertaRepo.save(alerta);
        return mapper.toDto(alerta);
    }

    public List<AlertaDTO> listarPorPonto(Long pontoId) {
        return alertaRepo.findByPontoColeta_Id(pontoId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AlertaDTO> listarTodos() {
        return alertaRepo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public AlertaDTO buscarPorId(Long id) {
        Alerta alerta = alertaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado"));
        return mapper.toDto(alerta);
    }

    @Transactional
    public void deletar(Long id) {
        if (!alertaRepo.existsById(id)) {
            throw new ResourceNotFoundException("Alerta não encontrado");
        }
        alertaRepo.deleteById(id);
    }
}
