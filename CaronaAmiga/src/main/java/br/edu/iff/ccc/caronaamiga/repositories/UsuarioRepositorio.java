package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Usuario;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmailInstitucional(String emailInstitucional);
    Optional<Usuario> findByMatricula(String matricula);
}