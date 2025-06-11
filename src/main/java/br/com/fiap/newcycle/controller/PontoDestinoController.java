package br.com.fiap.newcycle.controller;

import br.com.fiap.newcycle.api.dto.PontoDestinoDTO;
import br.com.fiap.newcycle.service.PontoDestinoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/destinos")
public class PontoDestinoController {

    private final PontoDestinoService service;

    public PontoDestinoController(PontoDestinoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PontoDestinoDTO> criar(@RequestBody @Valid PontoDestinoDTO dto) {
        PontoDestinoDTO salvo = service.criar(dto);
        return ResponseEntity.created(URI.create("/destinos/" + salvo.id())).body(salvo);
    }

    @GetMapping
    public List<PontoDestinoDTO> listar() { return service.listarTodos(); }

    @GetMapping("/{id}")
    public PontoDestinoDTO buscar(@PathVariable Long id) { return service.buscarPorId(id); }

    @PutMapping("/{id}")
    public PontoDestinoDTO atualizar(@PathVariable Long id,
                                     @RequestBody @Valid PontoDestinoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
