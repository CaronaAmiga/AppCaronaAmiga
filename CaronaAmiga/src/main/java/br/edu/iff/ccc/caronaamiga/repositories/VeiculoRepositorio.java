package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Veiculo;

@Repository
public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByMotoristaId(Long motoristaId);
    Optional<Veiculo> findByPlaca(String placa);
}