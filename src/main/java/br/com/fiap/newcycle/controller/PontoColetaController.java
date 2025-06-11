package br.com.fiap.newcycle.controller;

import br.com.fiap.newcycle.api.dto.PontoColetaDTO;
import br.com.fiap.newcycle.service.PontoColetaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pontos")
public class PontoColetaController {

    private final PontoColetaService service;

    public PontoColetaController(PontoColetaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PontoColetaDTO> criar(@RequestBody @Valid PontoColetaDTO dto) {
        PontoColetaDTO salvo = service.criar(dto);
        return ResponseEntity
                .created(URI.create("/pontos/" + salvo.id()))
                .body(salvo);
    }

    @GetMapping
    public List<PontoColetaDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public PontoColetaDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public PontoColetaDTO atualizar(@PathVariable Long id,
                                    @RequestBody @Valid PontoColetaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
