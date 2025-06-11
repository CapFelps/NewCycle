package br.com.fiap.newcycle.controller;

import br.com.fiap.newcycle.api.dto.AlertaDTO;
import br.com.fiap.newcycle.service.AlertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service) { this.service = service; }

    @GetMapping
    public List<AlertaDTO> listar() { return service.listarTodos(); }

    @GetMapping("/{id}")
    public AlertaDTO buscar(@PathVariable Long id) { return service.buscarPorId(id); }

    @GetMapping("/ponto/{pontoId}")
    public List<AlertaDTO> listarPorPonto(@PathVariable Long pontoId) {
        return service.listarPorPonto(pontoId);
    }

    @PostMapping("/ponto/{pontoId}")
    public AlertaDTO criar(@PathVariable Long pontoId,
                           @RequestBody String mensagem) {
        return service.criarManualmente(pontoId, mensagem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
