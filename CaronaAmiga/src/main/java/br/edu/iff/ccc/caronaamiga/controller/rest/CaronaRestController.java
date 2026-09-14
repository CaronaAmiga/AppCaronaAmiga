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

import br.edu.iff.ccc.caronaamiga.dto.CaronaDTO;
import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.services.CaronaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/caronas")
@Tag(name = "Caronas", description = "Endpoints de oferta, busca e ciclo de vida de caronas")
public class CaronaRestController {

    private final CaronaService caronaService;

    public CaronaRestController(CaronaService caronaService) {
        this.caronaService = caronaService;
    }

    @GetMapping
    @Operation(summary = "Listar caronas com filtros opcionais (destino, preço máximo)")
    public ResponseEntity<List<Carona>> listar(
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) Double maxPreco) {
        if (destino != null || maxPreco != null) {
            return ResponseEntity.ok(caronaService.buscarCaronas(null, destino, maxPreco));
        }
        return ResponseEntity.ok(caronaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar carona por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Carona encontrada"),
        @ApiResponse(responseCode = "404", description = "Carona não encontrada")
    })
    public ResponseEntity<Carona> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(caronaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Ofertar uma nova carona")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Carona criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou veículo não pertence ao motorista")
    })
    public ResponseEntity<Carona> criar(@Valid @RequestBody CaronaDTO dto) {
        Carona carona = caronaService.criarCarona(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(carona.getId())
                .toUri();
        return ResponseEntity.created(location).body(carona);
    }

    @PatchMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar carona (muda status para EM_ANDAMENTO)")
    public ResponseEntity<Void> iniciar(@PathVariable Long id) {
        caronaService.iniciarCarona(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/concluir")
    @Operation(summary = "Concluir carona (muda status para CONCLUIDA e gera histórico)")
    public ResponseEntity<Void> concluir(@PathVariable Long id) {
        caronaService.concluirCarona(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar carona")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        caronaService.cancelarCarona(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir carona por ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        caronaService.deletarCarona(id);
        return ResponseEntity.noContent().build();
    }
}