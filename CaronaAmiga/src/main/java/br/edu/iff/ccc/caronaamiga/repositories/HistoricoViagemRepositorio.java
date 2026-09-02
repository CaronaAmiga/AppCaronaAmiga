package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.HistoricoViagem;

@Repository
public interface HistoricoViagemRepositorio extends JpaRepository<HistoricoViagem, Long> {
    List<HistoricoViagem> findByUsuarioId(Long usuarioId);
}