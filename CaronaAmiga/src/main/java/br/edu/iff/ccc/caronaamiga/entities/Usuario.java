package br.edu.iff.ccc.caronaamiga.entities;

public class Usuario {
    private long id;
    private String nome;
    private String emailInstitucional;
    private String senhaHash;
    private String matricula;
    private String telefone;
    private double reputacao;
    private TipoPerfil perfilAtivo;

    public Usuario(long id, String nome, String emailInstitucional, String senhaHash, String matricula, String telefone, double reputacao, TipoPerfil perfilAtivo){
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

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
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
