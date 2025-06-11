package br.com.fiap.newcycle.controller;

import br.com.fiap.newcycle.api.dto.DestinacaoDTO;
import br.com.fiap.newcycle.service.DestinacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/destinacoes")
public class DestinacaoController {

    private final DestinacaoService service;

    public DestinacaoController(DestinacaoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<DestinacaoDTO> registrar(@RequestBody @Valid DestinacaoDTO dto) {
        DestinacaoDTO salvo = service.registrarEnvio(dto);
        return ResponseEntity.created(URI.create("/destinacoes/" + salvo.id())).body(salvo);
    }

    @GetMapping("/{id}")
    public DestinacaoDTO buscar(@PathVariable Long id) { return service.buscarPorId(id); }

    @GetMapping("/destino/{pontoDestinoId}")
    public List<DestinacaoDTO> listarPorDestino(@PathVariable Long pontoDestinoId) {
        return service.listarPorDestino(pontoDestinoId);
    }

    @PutMapping("/{id}")
    public DestinacaoDTO atualizar(@PathVariable Long id,
                                   @RequestBody @Valid DestinacaoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
