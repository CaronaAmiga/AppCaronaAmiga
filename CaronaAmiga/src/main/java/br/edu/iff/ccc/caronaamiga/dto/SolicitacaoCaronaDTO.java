package br.edu.iff.ccc.caronaamiga.dto;

public class SolicitacaoCaronaDTO {

    private Long caronaId;
    private Long passageiroId;

    public SolicitacaoCaronaDTO() {
    }

    public SolicitacaoCaronaDTO(Long caronaId, Long passageiroId) {
        this.caronaId = caronaId;
        this.passageiroId = passageiroId;
    }

    public Long getCaronaId() {
        return caronaId;
    }

    public void setCaronaId(Long caronaId) {
        this.caronaId = caronaId;
    }

    public Long getPassageiroId() {
        return passageiroId;
    }

    public void setPassageiroId(Long passageiroId) {
        this.passageiroId = passageiroId;
    }
}