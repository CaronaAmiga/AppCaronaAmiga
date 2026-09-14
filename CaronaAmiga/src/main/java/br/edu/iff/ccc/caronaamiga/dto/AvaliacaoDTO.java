package br.edu.iff.ccc.caronaamiga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para envio de avaliação de carona")
public class AvaliacaoDTO {

    @Schema(description = "Nota de 1 a 5 estrelas", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 1, message = "A nota mínima é 1.")
    @Max(value = 5, message = "A nota máxima é 5.")
    private int nota; 

    @Schema(description = "Comentário sobre a viagem", example = "Ótima carona! O motorista foi muito pontual e responsável.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O comentário da avaliação é obrigatório.")
    @Size(min = 5, max = 500, message = "O comentário deve ter entre 5 e 500 caracteres.")
    private String comentario;

    @Schema(description = "ID do usuário avaliador", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID do avaliador é obrigatório.")
    @Positive(message = "O ID do avaliador deve ser positivo.")
    private Long avaliadorId;

    @Schema(description = "ID do usuário avaliado", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID do avaliado é obrigatório.")
    @Positive(message = "O ID do avaliado deve ser positivo.")
    private Long avaliadoId;

    @Schema(description = "ID da carona realizada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID da carona é obrigatório.")
    @Positive(message = "O ID da carona deve ser positivo.")
    private Long caronaId;

    public AvaliacaoDTO() {
    }

    public AvaliacaoDTO(int nota, String comentario, Long avaliadorId, Long avaliadoId, Long caronaId) {
        this.nota = nota;
        this.comentario = comentario;
        this.avaliadorId = avaliadorId;
        this.avaliadoId = avaliadoId;
        this.caronaId = caronaId;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getAvaliadorId() {
        return avaliadorId;
    }

    public void setAvaliadorId(Long avaliadorId) {
        this.avaliadorId = avaliadorId;
    }

    public Long getAvaliadoId() {
        return avaliadoId;
    }

    public void setAvaliadoId(Long avaliadoId) {
        this.avaliadoId = avaliadoId;
    }

    public Long getCaronaId() {
        return caronaId;
    }

    public void setCaronaId(Long caronaId) {
        this.caronaId = caronaId;
    }
}