package br.edu.iff.ccc.caronaamiga.dto;

public class MensagemDTO {

    private String conteudo;
    private Long remetenteId;
    private Long destinatarioId;

    public MensagemDTO() {
    }

    public MensagemDTO(String conteudo, Long remetenteId, Long destinatarioId) {
        this.conteudo = conteudo;
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Long getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(Long remetenteId) {
        this.remetenteId = remetenteId;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }
}