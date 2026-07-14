package br.edu.iff.ccc.caronaamiga.entities;

import java.util.UUID;

public class Veiculo {

    private UUID id;
    private String modelo;
    private String cor;
    private String placa;
    private int quantidadeVagas;

    public Veiculo(UUID id, String modelo, String cor, String placa, int quantidadeVagas) {
        this.id = id;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.quantidadeVagas = quantidadeVagas;
    }   

    public Veiculo() {
    }

    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
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

    public int GetQuantidadeVagas(){
        return quantidadeVagas;
    }

    public void setQuantidadeVagas(int quantidadeVagas){
        this.quantidadeVagas = quantidadeVagas;
    }
}