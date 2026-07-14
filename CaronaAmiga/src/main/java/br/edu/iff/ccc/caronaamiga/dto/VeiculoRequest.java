package br.edu.iff.ccc.caronaamiga.dto;

public class VeiculoRequest {
    private String modelo;
    private String cor;
    private String placa;
    private int quantidadeVagas;

    public VeiculoRequest(String modelo, String cor, String placa, int quantidadeVagas) {
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.quantidadeVagas = quantidadeVagas;
    }

    public VeiculoRequest() {
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

}