package br.edu.iff.ccc.caronaamiga.dto;

import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;

public class UsuarioDTO {

    private Long id;
    private String nome;
    private String emailInstitucional;
    private String senha;
    private String matricula;
    private String telefone;
    private TipoPerfil perfilAtivo;

    
    public UsuarioDTO(Long id, String nome, String emailInstitucional, String senha, String matricula, String telefone, TipoPerfil perfilAtivo) {
        this.id = id;
        this.nome = nome;
        this.emailInstitucional = emailInstitucional;
        this.senha = senha;
        this.matricula = matricula;
        this.telefone = telefone;
        this.perfilAtivo = perfilAtivo;
    }
    
    public UsuarioDTO() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public TipoPerfil getPerfilAtivo() {
        return perfilAtivo;
    }

    public void setPerfilAtivo(TipoPerfil perfilAtivo) {
        this.perfilAtivo = perfilAtivo;
    }
}