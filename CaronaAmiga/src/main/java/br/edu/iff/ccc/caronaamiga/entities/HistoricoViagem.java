package br.edu.iff.ccc.caronaamiga.entities;

import java.time.LocalDateTime;

public class HistoricoViagem {
    private long id;
    private LocalDateTime dataParticipacao;
    private Usuario usuario;
    private Carona carona;

    public HistoricoViagem(long id, LocalDateTime dataParticipacao, Usuario usuario, Carona carona){
        this.id = id;
        this.dataParticipacao = dataParticipacao;
        this.usuario = usuario;
        this.carona = carona;
    }

    public HistoricoViagem(){

    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public LocalDateTime getDataParticipacao(){
        return dataParticipacao;
    }

    public void setDataParticipacao(LocalDateTime dataParticipacao){
        this.dataParticipacao = dataParticipacao;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public Carona getCarona(){
        return carona;
    }

    public void setCarona(Carona carona){
        this.carona = carona;
    }
}
