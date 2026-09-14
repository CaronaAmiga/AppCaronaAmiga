package br.edu.iff.ccc.caronaamiga.controller.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.iff.ccc.caronaamiga.dto.VeiculoDTO;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;
import br.edu.iff.ccc.caronaamiga.services.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/veiculos")
@Tag(name = "Veículos", description = "Endpoints de gerenciamento e consulta de veículos")
public class VeiculoRestController {

    private final VeiculoService veiculoService;

    public VeiculoRestController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    @Operation(summary = "Listar veículos (todos ou por motorista)")
    public ResponseEntity<List<Veiculo>> listar(@RequestParam(required = false) Long motoristaId) {
        if (motoristaId != null) {
            return ResponseEntity.ok(veiculoService.listarPorMotoristaId(motoristaId));
        }
        return ResponseEntity.ok(veiculoService.listarVeiculo());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veículo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Veículo localizado"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo veículo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou proprietário não é motorista"),
        @ApiResponse(responseCode = "409", description = "Placa já cadastrada")
    })
    public ResponseEntity<Veiculo> criar(@Valid @RequestBody VeiculoDTO dto) {
        Veiculo novo = veiculoService.criarVeiculo(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novo.getId())
                .toUri();
        return ResponseEntity.created(location).body(novo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um veículo")
    public ResponseEntity<Veiculo> atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoDTO dto) {
        veiculoService.atualizarVeiculo(id, dto);
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir veículo por ID")
    @ApiResponse(responseCode = "204", description = "Veículo excluído com sucesso")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veiculoService.deletarVeiculo(id);
        return ResponseEntity.noContent().build();
    }
}