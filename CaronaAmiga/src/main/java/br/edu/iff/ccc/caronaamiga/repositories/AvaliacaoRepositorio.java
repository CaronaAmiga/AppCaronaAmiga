package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Avaliacao;

@Repository
public interface AvaliacaoRepositorio extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByAvaliadoId(Long usuarioId);
}