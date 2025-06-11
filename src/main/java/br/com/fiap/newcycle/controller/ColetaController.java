package br.com.fiap.newcycle.controller;

import br.com.fiap.newcycle.api.dto.ColetaDTO;
import br.com.fiap.newcycle.service.ColetaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/coletas")
public class ColetaController {

    private final ColetaService service;

    public ColetaController(ColetaService service) { this.service = service; }

    @PostMapping("/ponto/{pontoId}")
    public ResponseEntity<ColetaDTO> agendar(@PathVariable Long pontoId,
                                             @RequestBody @Valid ColetaDTO dto) {
        ColetaDTO salvo = service.agendar(pontoId, dto);
        return ResponseEntity.created(URI.create("/coletas/" + salvo.id())).body(salvo);
    }

    @GetMapping("/{id}")
    public ColetaDTO buscar(@PathVariable Long id) { return service.buscarPorId(id); }

    @GetMapping("/ponto/{pontoId}")
    public List<ColetaDTO> listarPorPonto(@PathVariable Long pontoId) {
        return service.listarPorPonto(pontoId);
    }

    @PostMapping("/{id}/realizar")
    public ColetaDTO marcarRealizada(@PathVariable Long id) {
        return service.marcarRealizada(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
