package br.edu.iff.ccc.caronaamiga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro e atualização de veículo")
public class VeiculoDTO {
    
    @Schema(description = "Identificador único do veículo", example = "1")
    private Long id;

    @Schema(description = "Modelo do veículo", example = "Fiat Argo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O modelo do veículo é obrigatório.")
    @Size(min = 2, max = 50, message = "O modelo deve ter entre 2 e 50 caracteres.")
    private String modelo;

    @Schema(description = "Cor do veículo", example = "Prata", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A cor do veículo é obrigatória.")
    @Size(min = 3, max = 30, message = "A cor deve ter entre 3 e 30 caracteres.")
    private String cor;

    @Schema(description = "Placa no formato tradicional (ABC-1234) ou Mercosul (ABC1D23)", example = "RIO2A18", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A placa do veículo é obrigatória.")
    @Pattern(regexp = "^[A-Za-z]{3}-?[0-9][A-Za-z0-9][0-9]{2}$", message = "Placa inválida. Utilize o formato tradicional (ABC-1234) ou Mercosul (ABC1D23).")
    private String placa;

    @Schema(description = "Quantidade de vagas para passageiros", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 1, message = "O veículo deve ter pelo menos 1 vaga.")
    @Max(value = 8, message = "O veículo pode ter no máximo 8 vagas.")
    private int quantidadeVagas;

    @Schema(description = "ID do motorista proprietário", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Selecione o motorista proprietário do veículo.")
    private Long motoristaId;

    public VeiculoDTO() {
    }

    public VeiculoDTO(Long id, String modelo, String cor, String placa, int quantidadeVagas, Long motoristaId) {
        this.id = id;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.quantidadeVagas = quantidadeVagas;
        this.motoristaId = motoristaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getQuantidadeVagas() {
        return quantidadeVagas;
    }

    public void setQuantidadeVagas(int quantidadeVagas) {
        this.quantidadeVagas = quantidadeVagas;
    }

    public Long getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(Long motoristaId) {
        this.motoristaId = motoristaId;
    }
}