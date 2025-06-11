package br.com.fiap.newcycle.service;

import br.com.fiap.newcycle.api.dto.ColetaDTO;
import br.com.fiap.newcycle.api.mapper.ColetaMapper;
import br.com.fiap.newcycle.domain.model.Coleta;
import br.com.fiap.newcycle.domain.model.PontoColeta;
import br.com.fiap.newcycle.domain.repository.ColetaRepository;
import br.com.fiap.newcycle.domain.repository.PontoColetaRepository;
import br.com.fiap.newcycle.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColetaService {

    private final ColetaRepository coletaRepo;
    private final PontoColetaRepository pontoRepo;
    private final ColetaMapper mapper;

    public ColetaService(ColetaRepository coletaRepo,
                         PontoColetaRepository pontoRepo,
                         ColetaMapper mapper) {
        this.coletaRepo = coletaRepo;
        this.pontoRepo  = pontoRepo;
        this.mapper     = mapper;
    }

    @Transactional
    public ColetaDTO agendar(Long pontoColetaId, ColetaDTO dto) {

        PontoColeta ponto = pontoRepo.findById(pontoColetaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado"));

        String status = "Agendada";
        String frequencia = dto.frequencia() == null ? "Nenhuma" : dto.frequencia();

        Coleta coleta = new Coleta(
                dto.dataAgendada(),
                status,
                frequencia,
                ponto
        );

        coleta = coletaRepo.save(coleta);
        return mapper.toDto(coleta);
    }

    public List<ColetaDTO> listarPorPonto(Long pontoId) {
        return coletaRepo.findByPontoColeta_IdAndStatus(pontoId, "Agendada")
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ColetaDTO buscarPorId(Long id) {
        return mapper.toDto(coletaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coleta não encontrada")));
    }

    @Transactional
    public ColetaDTO marcarRealizada(Long coletaId) {

        Coleta coleta = coletaRepo.findById(coletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Coleta não encontrada"));

        coleta.setStatus("Realizada");
        coleta.setDataRealizada(LocalDate.now());
        coleta = coletaRepo.save(coleta);

        gerarProximaSeRecorrente(coleta);

        return mapper.toDto(coleta);
    }

    @Transactional
    public void atualizarColetasAtrasadas() {
        LocalDate hoje = LocalDate.now();
        List<Coleta> atrasadas = coletaRepo.findByDataAgendadaBeforeAndDataRealizadaIsNull(hoje);
        for (Coleta c : atrasadas) {
            c.setStatus("Atrasada");
        }
        coletaRepo.saveAll(atrasadas);
    }

    @Transactional
    public void deletar(Long id) {
        if (!coletaRepo.existsById(id)) {
            throw new ResourceNotFoundException("Coleta não encontrada");
        }
        coletaRepo.deleteById(id);
    }

    private void gerarProximaSeRecorrente(Coleta coleta) {

        String freq = coleta.getFrequencia();
        if ("Nenhuma".equalsIgnoreCase(freq)) return;

        LocalDate novaData;
        switch (freq.toUpperCase()) {
            case "SEMANAL"   -> novaData = coleta.getDataAgendada().plusWeeks(1);
            case "QUINZENAL" -> novaData = coleta.getDataAgendada().plusWeeks(2);
            case "MENSAL"    -> novaData = coleta.getDataAgendada().plusMonths(1);
            default          -> { return; } // frequência desconhecida
        }

        Coleta nova = new Coleta(
                novaData,
                "Agendada",
                freq,
                coleta.getPontoColeta()
        );
        coletaRepo.save(nova);
    }
}
