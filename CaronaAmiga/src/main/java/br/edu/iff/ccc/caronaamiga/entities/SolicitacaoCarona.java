package br.edu.iff.ccc.caronaamiga.entities;

import java.time.LocalDateTime;

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
@Table(name = "tb_solicitacao_carona")
public class SolicitacaoCarona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataSolicitacao;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    @ManyToOne
    @JoinColumn(name = "passageiro_id")
    private Usuario passageiro;

    @ManyToOne
    @JoinColumn(name = "carona_id")
    private Carona carona;

    public SolicitacaoCarona(Long id, LocalDateTime dataSolicitacao, StatusSolicitacao status, Usuario passageiro, Carona carona){
        this.id = id;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.passageiro = passageiro;
        this.carona = carona;
    }

    public SolicitacaoCarona(){

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
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
