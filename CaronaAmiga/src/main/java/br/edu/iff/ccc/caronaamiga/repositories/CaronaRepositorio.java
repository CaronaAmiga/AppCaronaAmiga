package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Carona;

@Repository
public interface CaronaRepositorio extends JpaRepository<Carona, Long> {
    List<Carona> findByDestinoContainingIgnoreCase(String destino);
    List<Carona> findByMotoristaId(Long motoristaId);
}