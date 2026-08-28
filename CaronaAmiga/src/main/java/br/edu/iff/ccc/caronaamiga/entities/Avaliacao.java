package br.edu.iff.ccc.caronaamiga.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nota;
    private String comentario;
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "avaliador_id")
    private Usuario avaliador;

    @ManyToOne
    @JoinColumn(name = "avaliado_id")
    private Usuario avaliado;

    @ManyToOne
    @JoinColumn(name = "carona_id")
    private Carona carona;

    public Avaliacao(Long id, int nota, String comentario, LocalDateTime data, Usuario avaliador, Usuario avaliado, Carona carona){
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

    public Long getId(){
        return id;
    }

    public void setId(Long id){
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
