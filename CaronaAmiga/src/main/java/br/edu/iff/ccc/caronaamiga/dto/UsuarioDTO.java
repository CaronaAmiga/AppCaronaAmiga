package br.edu.iff.ccc.caronaamiga.dto;

import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro e atualização de usuário")
public class UsuarioDTO {
    
    @Schema(description = "Identificador único do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @Schema(description = "E-mail institucional do IFF", example = "joao.silva@iff.edu.br", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O e-mail institucional é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String emailInstitucional;

    @Schema(description = "Senha de acesso", example = "Senha@123", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 50, message = "A senha deve ter no máximo 50 caracteres.")
    private String senha;

    @Schema(description = "Matrícula acadêmica ou funcional", example = "202410019287", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A matrícula acadêmica é obrigatória.")
    @Size(min = 12, max = 20, message = "A matrícula deve ter entre 12 e 20 caracteres.")
    private String matricula;

    @Schema(description = "Telefone para contato com DDD", example = "(22) 99887-6655", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O telefone de contato é obrigatório.")
    private String telefone;

    @Schema(description = "Perfil ativo no sistema", example = "PASSAGEIRO", requiredMode = Schema.RequiredMode.REQUIRED)
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