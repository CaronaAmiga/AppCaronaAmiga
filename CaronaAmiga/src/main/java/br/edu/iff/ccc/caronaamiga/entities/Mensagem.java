package br.edu.iff.ccc.caronaamiga.entities;

import java.time.LocalDateTime;

public class Mensagem {
    private long id;
    private String conteudo;
    private LocalDateTime dataHora;
    private Usuario remetente;
    private Usuario destinatario;

    public Mensagem(long id, String conteudo, LocalDateTime dataHora, Usuario remetente, Usuario destinatario){
        this.id = id;
        this.conteudo = conteudo;
        this.dataHora = dataHora;
        this.remetente = remetente;
        this.destinatario = destinatario;
    }

    public Mensagem(){

    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getConteudo(){
        return conteudo;
    }

    public void setConteudo(String conteudo){
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataHora(){
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora){
        this.dataHora = dataHora;
    }

    public Usuario getRemetente(){
        return remetente;
    }

    public void setRemetente(Usuario remetente){
        this.remetente = remetente;
    }

    public Usuario getDestinatario(){
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario){
        this.destinatario = destinatario;
    }
}
