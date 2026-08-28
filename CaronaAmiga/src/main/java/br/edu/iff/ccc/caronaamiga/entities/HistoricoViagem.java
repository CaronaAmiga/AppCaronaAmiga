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
@Table(name = "tb_historico_viagem")
public class HistoricoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataParticipacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "carona_id")
    private Carona carona;

    public HistoricoViagem(Long id, LocalDateTime dataParticipacao, Usuario usuario, Carona carona){
        this.id = id;
        this.dataParticipacao = dataParticipacao;
        this.usuario = usuario;
        this.carona = carona;
    }

    public HistoricoViagem(){

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
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
