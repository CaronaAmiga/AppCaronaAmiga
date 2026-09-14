package br.edu.iff.ccc.caronaamiga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados para solicitação de vaga em carona")
public class SolicitacaoCaronaDTO {

    @Schema(description = "ID da carona desejada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID da carona é obrigatório.")
    @Positive(message = "O ID da carona deve ser positivo.")
    private Long caronaId;

    @Schema(description = "ID do passageiro solicitante", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID do passageiro é obrigatório.")
    @Positive(message = "O ID do passageiro deve ser positivo.")
    private Long passageiroId;

    public SolicitacaoCaronaDTO() {
    }

    public SolicitacaoCaronaDTO(Long caronaId, Long passageiroId) {
        this.caronaId = caronaId;
        this.passageiroId = passageiroId;
    }

    public Long getCaronaId() {
        return caronaId;
    }

    public void setCaronaId(Long caronaId) {
        this.caronaId = caronaId;
    }

    public Long getPassageiroId() {
        return passageiroId;
    }

    public void setPassageiroId(Long passageiroId) {
        this.passageiroId = passageiroId;
    }
}