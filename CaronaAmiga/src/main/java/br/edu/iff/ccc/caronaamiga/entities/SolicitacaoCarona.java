package br.edu.iff.ccc.caronaamiga.entities;

import java.time.LocalDateTime;

public class SolicitacaoCarona {
    private long id;
    private LocalDateTime dataSolicitacao;
    private StatusSolicitacao status;
    private Usuario passageiro;
    private Carona carona;

    public SolicitacaoCarona(long id, LocalDateTime dataSolicitacao, StatusSolicitacao status, Usuario passageiro, Carona carona){
        this.id = id;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.passageiro = passageiro;
        this.carona = carona;
    }

    public SolicitacaoCarona(){

    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public LocalDateTime getDataSolicitacao(){
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao){
        this.dataSolicitacao = dataSolicitacao;
    }

    public StatusSolicitacao getStatus(){
        return status;
    }

    public void setStatus(StatusSolicitacao status){
        this.status = status;
    }

    public Usuario getPassageiro(){
        return passageiro;
    }

    public void setPassageiro(Usuario passageiro){
        this.passageiro = passageiro;
    }

    public Carona getCarona(){
        return carona;
    }

    public void setCarona(Carona carona){
        this.carona = carona;
    }

    public void aprovar(){
        this.status = StatusSolicitacao.ACEITA;
        this.carona.decrementarVaga();
    }

    public void recusar(){
        this.status = StatusSolicitacao.RECUSADA;
    }

    public void cancelar(){
        this.status = StatusSolicitacao.CANCELADA;
    }
}
