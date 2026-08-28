package br.edu.iff.ccc.caronaamiga.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String emailInstitucional;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false, unique = true)
    private String matricula;
    private String telefone;
    private double reputacao;

    @Enumerated(EnumType.STRING)
    private TipoPerfil perfilAtivo;

    public Usuario(Long id, String nome, String emailInstitucional, String senhaHash, String matricula, String telefone, double reputacao, TipoPerfil perfilAtivo){
        this.id = id;
        this.nome = nome;
        this.emailInstitucional = emailInstitucional;
        this.senhaHash = senhaHash;
        this.matricula = matricula;
        this.telefone = telefone;
        this.reputacao = reputacao;
        this.perfilAtivo = perfilAtivo;
    }

    public Usuario(){
    
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }
    
    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }

    public String getSenhaHash() {
        return senhaHash;
    }
    
    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getReputacao() {
        return reputacao;
    }
    
    public void setReputacao(double reputacao) {
        this.reputacao = reputacao;
    }

    public TipoPerfil getPerfilAtivo() {
        return perfilAtivo;
    }
    
    public void setPerfilAtivo(TipoPerfil perfilAtivo) {
        this.perfilAtivo = perfilAtivo;
    }

    public void alternarPerfil(){
        if(this.perfilAtivo == TipoPerfil.MOTORISTA){
            this.perfilAtivo = TipoPerfil.PASSAGEIRO;
        }

        else{
            this.perfilAtivo = TipoPerfil.MOTORISTA;
        }
    }
}
