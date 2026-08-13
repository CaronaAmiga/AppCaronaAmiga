package br.edu.iff.ccc.caronaamiga.entities;

import java.time.LocalDateTime;

public class Avaliacao {
    private long id;
    private int nota;
    private String comentario;
    private LocalDateTime data;
    private Usuario avaliador;
    private Usuario avaliado;
    private Carona carona;

    public Avaliacao(long id, int nota, String comentario, LocalDateTime data, Usuario avaliador, Usuario avaliado, Carona carona){
        this.id = id;
        this.nota = nota;
        this.comentario = comentario;
        this.data = data;
        this.avaliador = avaliador;
        this.avaliado = avaliado;
        this.carona = carona;
    }

    public Avaliacao(){

    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getNota(){
        return nota;
    }

    public void setNota(int nota){
        this.nota = nota;
    }

    public String getComentario(){
        return comentario;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }

    public LocalDateTime getData(){
        return data;
    }

    public void setData(LocalDateTime data){
        this.data = data;
    }

    public Usuario getAvaliador(){
        return avaliador;
    }

    public void setAvaliador(Usuario avaliador){
        this.avaliador = avaliador;
    }

    public Usuario getAvaliado(){
        return avaliado;
    }

    public void setAvaliado(Usuario avaliado){
        this.avaliado = avaliado;
    }

    public Carona getCarona(){
        return carona;
    }

    public void setCarona(Carona carona){
        this.carona = carona;
    }
}
