package br.edu.iff.ccc.caronaamiga.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_carona")
public class Carona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origem;
    private String destino;
    private LocalDate data;
    private LocalTime horarioPartida;
    private double valorRateio;
    private int vagasDisponiveis;
    private double quilometragem;

    @Enumerated(EnumType.STRING)
    private StatusCarona status;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Usuario motorista;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    public Carona(Long id, String origem, String destino, LocalDate data, LocalTime horarioPartida, double valorRateio, int vagasDisponiveis, double quilometragem, StatusCarona status, Usuario motorista, Veiculo veiculo){
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.horarioPartida = horarioPartida;
        this.valorRateio = valorRateio;
        this.vagasDisponiveis = vagasDisponiveis;
        this.quilometragem = quilometragem;
        this.status = status;
        this.motorista = motorista;
        this.veiculo = veiculo;
    }

    public Carona(){

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

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public StatusCarona getStatus() {
        return status;
    }

    public void setStatus(StatusCarona status) {
        this.status = status;
    }

    public Usuario getMotorista() {
        return motorista;
    }

    public void setMotorista(Usuario motorista) {
        this.motorista = motorista;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public void iniciarCarona(){
        this.status = StatusCarona.EM_ANDAMENTO;
    }

    public void concluirCarona(){
        this.status = StatusCarona.CONCLUIDA;
    }

    public void cancelarCarona(){
        this.status = StatusCarona.CANCELADA;
    }

    public void decrementarVaga(){
        if(vagasDisponiveis > 0){
            vagasDisponiveis -= 1;
        }
    }

    public double calcularRateio(){
        if (this.quilometragem <= 0) {
            return 0.0;
        }

        double consumoKmPorLitro = 10.0;
        double precoCombustivelLitro = 5.80;

        int totalOcupantes;

        if (this.veiculo != null && this.veiculo.getQuantidadeVagas() > 0) {
            totalOcupantes = this.veiculo.getQuantidadeVagas() + 1;
        } 
    
        else {
            totalOcupantes = 4;
        }

        double litrosNecessarios = this.quilometragem / consumoKmPorLitro;
        double custoTotal = litrosNecessarios * precoCombustivelLitro;

        double rateioSugerido = custoTotal / totalOcupantes;

        this.valorRateio = Math.round(rateioSugerido * 100.0) / 100.0;
        return this.valorRateio;
    }
}
