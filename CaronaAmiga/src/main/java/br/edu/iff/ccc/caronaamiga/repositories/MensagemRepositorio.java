package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Mensagem;

@Repository
public interface MensagemRepositorio extends JpaRepository<Mensagem, Long> {
    
    @Query("SELECT m FROM Mensagem m WHERE (m.remetente.id = :u1 AND m.destinatario.id = :u2) OR (m.remetente.id = :u2 AND m.destinatario.id = :u1) ORDER BY m.dataHora ASC")
    List<Mensagem> findConversa(@Param("u1") Long idUsuario1, @Param("u2") Long idUsuario2);
}