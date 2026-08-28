package br.edu.iff.ccc.caronaamiga.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_veiculo")

public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;
    private String cor;

    @Column(nullable = false, unique = true)
    private String placa;
    private int quantidadeVagas;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Usuario motorista;

    public Veiculo(Long id, String modelo, String cor, String placa, int quantidadeVagas, Usuario motorista) {
        this.id = id;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.quantidadeVagas = quantidadeVagas;
        this.motorista = motorista;
    }   

    public Veiculo() {

    }

    public void atualizarDados(String modelo, String cor, String placa, int quantidadeVagas){
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.quantidadeVagas = quantidadeVagas;
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

    public String getPlaca(){
        return placa;
    }

    public void setPlaca(String placa){
        this.placa = placa;
    }

    public int getQuantidadeVagas(){
        return quantidadeVagas;
    }

    public void setQuantidadeVagas(int quantidadeVagas){
        this.quantidadeVagas = quantidadeVagas;
    }

    public Usuario getMotorista(){
        return motorista;
    }

    public void setMotorista(Usuario motorista){
        this.motorista = motorista;
    }
}