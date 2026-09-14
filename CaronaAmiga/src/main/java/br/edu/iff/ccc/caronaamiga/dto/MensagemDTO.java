package br.edu.iff.ccc.caronaamiga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para envio de mensagem privada entre usuários")
public class MensagemDTO {

    @Schema(description = "Conteúdo textual da mensagem", example = "Olá! Onde podemos nos encontrar para a saída da carona?", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O conteúdo da mensagem não pode ser vazio.")
    @Size(min = 1, max = 1000, message = "A mensagem pode ter no máximo 1000 caracteres.")
    private String conteudo;

    @Schema(description = "ID do usuário remetente", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O remetente é obrigatório.")
    @Positive(message = "O ID do remetente deve ser positivo.")
    private Long remetenteId;

    @Schema(description = "ID do usuário destinatário", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O destinatário é obrigatório.")
    @Positive(message = "O ID do destinatário deve ser positivo.")
    private Long destinatarioId;

    public MensagemDTO() {
    }

    public MensagemDTO(String conteudo, Long remetenteId, Long destinatarioId) {
        this.conteudo = conteudo;
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Long getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(Long remetenteId) {
        this.remetenteId = remetenteId;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }
}