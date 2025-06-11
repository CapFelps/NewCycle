package br.com.fiap.newcycle.controller;

import br.com.fiap.newcycle.api.dto.ResiduoDTO;
import br.com.fiap.newcycle.service.ResiduoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/residuos")
public class ResiduoController {

    private final ResiduoService service;

    public ResiduoController(ResiduoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResiduoDTO> criar(@RequestBody @Valid ResiduoDTO dto) {
        ResiduoDTO salvo = service.criar(dto);
        return ResponseEntity.created(URI.create("/residuos/" + salvo.id())).body(salvo);
    }

    @GetMapping
    public List<ResiduoDTO> listar() { return service.listarTodos(); }

    @GetMapping("/{id}")
    public ResiduoDTO buscar(@PathVariable Long id) { return service.buscarPorId(id); }

    @GetMapping("/ponto/{pontoId}")
    public List<ResiduoDTO> listarPorPonto(@PathVariable Long pontoId) {
        return service.listarPorPonto(pontoId);
    }

    @PutMapping("/{id}")
    public ResiduoDTO atualizar(@PathVariable Long id,
                                @RequestBody @Valid ResiduoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
