package br.edu.iff.ccc.caronaamiga.controller.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.iff.ccc.caronaamiga.dto.SolicitacaoCaronaDTO;
import br.edu.iff.ccc.caronaamiga.entities.SolicitacaoCarona;
import br.edu.iff.ccc.caronaamiga.services.SolicitacaoCaronaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/solicitacoes")
@Tag(name = "Solicitações de Carona", description = "Endpoints de solicitação, aprovação e recusa de vagas em caronas")
public class SolicitacaoCaronaRestController {

    private final SolicitacaoCaronaService solicitacaoService;

    public SolicitacaoCaronaRestController(SolicitacaoCaronaService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping
    @Operation(summary = "Listar solicitações (todas, por carona ou por passageiro)")
    public ResponseEntity<List<SolicitacaoCarona>> listar(
            @RequestParam(required = false) Long caronaId,
            @RequestParam(required = false) Long passageiroId) {
        if (caronaId != null) {
            return ResponseEntity.ok(solicitacaoService.listarPorCarona(caronaId));
        }
        if (passageiroId != null) {
            return ResponseEntity.ok(solicitacaoService.listarPorPassageiro(passageiroId));
        }
        return ResponseEntity.ok(solicitacaoService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar solicitação por ID")
    public ResponseEntity<SolicitacaoCarona> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitacaoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Solicitar vaga em uma carona")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Solicitação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Sem vagas ou motorista pediu carona para si mesmo"),
        @ApiResponse(responseCode = "409", description = "Passageiro já possui solicitação nesta carona")
    })
    public ResponseEntity<SolicitacaoCarona> solicitar(@Valid @RequestBody SolicitacaoCaronaDTO dto) {
        SolicitacaoCarona solicitacao = solicitacaoService.solicitarVaga(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(solicitacao.getId())
                .toUri();
        return ResponseEntity.created(location).body(solicitacao);
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar solicitação (motorista aceita passageiro e desconta vaga)")
    public ResponseEntity<Void> aprovar(@PathVariable Long id) {
        solicitacaoService.aprovar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/recusar")
    @Operation(summary = "Recusar solicitação de carona")
    public ResponseEntity<Void> recusar(@PathVariable Long id) {
        solicitacaoService.recusar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar solicitação de carona")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        solicitacaoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir solicitação")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        solicitacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}