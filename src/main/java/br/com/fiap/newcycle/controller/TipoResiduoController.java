package br.com.fiap.newcycle.controller;

import br.com.fiap.newcycle.api.dto.TipoResiduoDTO;
import br.com.fiap.newcycle.service.TipoResiduoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tipos-residuo")
public class TipoResiduoController {

    private final TipoResiduoService service;

    public TipoResiduoController(TipoResiduoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<TipoResiduoDTO> criar(@RequestBody @Valid TipoResiduoDTO dto) {
        TipoResiduoDTO salvo = service.criar(dto);
        return ResponseEntity.created(URI.create("/tipos-residuo/" + salvo.id())).body(salvo);
    }

    @GetMapping
    public List<TipoResiduoDTO> listar() { return service.listarTodos(); }

    @GetMapping("/{id}")
    public TipoResiduoDTO buscar(@PathVariable Long id) { return service.buscarPorId(id); }

    @PutMapping("/{id}")
    public TipoResiduoDTO atualizar(@PathVariable Long id,
                                    @RequestBody @Valid TipoResiduoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
