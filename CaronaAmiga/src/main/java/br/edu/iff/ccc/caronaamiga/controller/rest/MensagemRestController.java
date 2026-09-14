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

import br.edu.iff.ccc.caronaamiga.dto.MensagemDTO;
import br.edu.iff.ccc.caronaamiga.entities.Mensagem;
import br.edu.iff.ccc.caronaamiga.services.MensagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mensagens")
@Tag(name = "Mensagens", description = "Endpoints de chat e mensagens entre usuários")
public class MensagemRestController {

    private final MensagemService mensagemService;

    public MensagemRestController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as mensagens ou buscar conversa entre dois usuários")
    public ResponseEntity<List<Mensagem>> listar(
            @RequestParam(required = false) Long usuario1,
            @RequestParam(required = false) Long usuario2) {
        if (usuario1 != null && usuario2 != null) {
            return ResponseEntity.ok(mensagemService.obterConversa(usuario1, usuario2));
        }
        return ResponseEntity.ok(mensagemService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mensagem por ID")
    public ResponseEntity<Mensagem> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mensagemService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Enviar uma nova mensagem")
    public ResponseEntity<Mensagem> enviar(@Valid @RequestBody MensagemDTO dto) {
        Mensagem mensagem = mensagemService.enviarMensagem(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(mensagem.getId())
                .toUri();
        return ResponseEntity.created(location).body(mensagem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir mensagem por ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mensagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}