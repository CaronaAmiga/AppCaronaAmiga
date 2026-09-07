package br.edu.iff.ccc.caronaamiga.dto;

import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail institucional é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String emailInstitucional;

    @Size(max = 50, message = "A senha deve ter no máximo 50 caracteres.")
    private String senha;

    @NotBlank(message = "A matrícula acadêmica é obrigatória.")
    @Size(min = 6, max = 20, message = "A matrícula deve ter entre 6 e 20 caracteres.")
    private String matricula;

    @NotBlank(message = "O telefone de contato é obrigatório.")
    private String telefone;

    @NotNull(message = "Selecione o perfil do usuário.")
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