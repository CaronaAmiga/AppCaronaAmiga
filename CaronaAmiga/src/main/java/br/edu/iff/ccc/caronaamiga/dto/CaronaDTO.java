package br.edu.iff.ccc.caronaamiga.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class CaronaDTO {
    private Long id;

    @NotBlank(message = "O local de origem é obrigatório.")
    @Size(min = 3, max = 100, message = "A origem deve ter entre 3 e 100 caracteres.")
    private String origem;

    @NotBlank(message = "O destino é obrigatório.")
    @Size(min = 3, max = 100, message = "O destino deve ter entre 3 e 100 caracteres.")
    private String destino;

    @NotNull(message = "A data da viagem é obrigatória.")
    @FutureOrPresent(message = "A data da carona deve ser hoje ou uma data futura.")
    private LocalDate data;

    @NotNull(message = "O horário de partida é obrigatório.")
    private LocalTime horarioPartida;

    @Positive(message = "A distância estimada deve ser maior que zero.")
    private double quilometragem;

    @PositiveOrZero(message = "O valor de rateio não pode ser negativo.")
    private double valorRateio;

    @Min(value = 1, message = "A carona deve oferecer pelo menos 1 vaga.")
    @Max(value = 8, message = "A carona pode oferecer no máximo 8 vagas.")
    private int vagasDisponiveis;

    @NotNull(message = "Selecione o motorista condutor.")
    private Long motoristaId;

    @NotNull(message = "Selecione o veículo da carona.")
    private Long veiculoId;

    public CaronaDTO() {
    }

    public CaronaDTO(Long id, String origem, String destino, LocalDate data, LocalTime horarioPartida, double quilometragem, double valorRateio, int vagasDisponiveis, Long motoristaId, Long veiculoId) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.horarioPartida = horarioPartida;
        this.quilometragem = quilometragem;
        this.valorRateio = valorRateio;
        this.vagasDisponiveis = vagasDisponiveis;
        this.motoristaId = motoristaId;
        this.veiculoId = veiculoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorarioPartida() {
        return horarioPartida;
    }

    public void setHorarioPartida(LocalTime horarioPartida) {
        this.horarioPartida = horarioPartida;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public double getValorRateio() {
        return valorRateio;
    }

    public void setValorRateio(double valorRateio) {
        this.valorRateio = valorRateio;
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(int vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Long getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(Long motoristaId) {
        this.motoristaId = motoristaId;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }
}