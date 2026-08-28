package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.SolicitacaoCarona;

@Repository
public interface SolicitacaoCaronaRepositorio extends JpaRepository<SolicitacaoCarona, Long> {
    List<SolicitacaoCarona> findByCaronaId(Long caronaId);
    List<SolicitacaoCarona> findByPassageiroId(Long passageiroId);
}