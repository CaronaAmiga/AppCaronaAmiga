package br.edu.iff.ccc.caronaamiga.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CaronaDTO {

    private Long id;
    private String origem;
    private String destino;
    private LocalDate data;
    private LocalTime horarioPartida;
    private double quilometragem;
    private double valorRateio;
    private int vagasDisponiveis;
    private Long motoristaId;
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