package br.edu.iff.ccc.caronaamiga.dto;

public class AvaliacaoDTO {

    private int nota; 
    private String comentario;
    private Long avaliadorId;
    private Long avaliadoId;
    private Long caronaId;

    public AvaliacaoDTO() {
    }

    public AvaliacaoDTO(int nota, String comentario, Long avaliadorId, Long avaliadoId, Long caronaId) {
        this.nota = nota;
        this.comentario = comentario;
        this.avaliadorId = avaliadorId;
        this.avaliadoId = avaliadoId;
        this.caronaId = caronaId;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getAvaliadorId() {
        return avaliadorId;
    }

    public void setAvaliadorId(Long avaliadorId) {
        this.avaliadorId = avaliadorId;
    }

    public Long getAvaliadoId() {
        return avaliadoId;
    }

    public void setAvaliadoId(Long avaliadoId) {
        this.avaliadoId = avaliadoId;
    }

    public Long getCaronaId() {
        return caronaId;
    }

    public void setCaronaId(Long caronaId) {
        this.caronaId = caronaId;
    }
}