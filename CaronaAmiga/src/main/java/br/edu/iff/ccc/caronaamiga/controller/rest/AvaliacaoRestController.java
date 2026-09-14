package br.edu.iff.ccc.caronaamiga.controller.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.iff.ccc.caronaamiga.dto.AvaliacaoDTO;
import br.edu.iff.ccc.caronaamiga.entities.Avaliacao;
import br.edu.iff.ccc.caronaamiga.services.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/avaliacoes")
@Tag(name = "Avaliações", description = "Endpoints para envio e consulta de avaliações de usuários após as viagens")
public class AvaliacaoRestController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoRestController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping
    @Operation(summary = "Listar avaliações (todas ou filtradas por usuário avaliado)")
    public ResponseEntity<List<Avaliacao>> listar(@RequestParam(required = false) Long avaliadoId) {
        if (avaliadoId != null) {
            return ResponseEntity.ok(avaliacaoService.listarPorAvaliado(avaliadoId));
        }
        return ResponseEntity.ok(avaliacaoService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(avaliacaoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar avaliação (recalcula automaticamente a reputação do avaliado)")
    public ResponseEntity<Avaliacao> avaliar(@Valid @RequestBody AvaliacaoDTO dto) {
        Avaliacao avaliacao = avaliacaoService.avaliar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(avaliacao.getId())
                .toUri();
        return ResponseEntity.created(location).body(avaliacao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir avaliação")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}