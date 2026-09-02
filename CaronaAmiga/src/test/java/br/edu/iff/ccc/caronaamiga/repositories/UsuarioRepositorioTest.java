package br.edu.iff.ccc.caronaamiga.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;

@DataJpaTest
class UsuarioRepositorioTest {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    @DisplayName("1. Deve gravar usuário com sucesso no banco H2")
    void deveSalvarUsuarioComSucesso() {
        Usuario usuario = new Usuario(
            null,
            "João Silva",
            "joao.silva@gsuite.iff.edu.br",
            "senha123",
            "20241001",
            "(22)99999-0000",
            5.0,
            TipoPerfil.PASSAGEIRO
        );

        Usuario usuarioSalvo = usuarioRepositorio.save(usuario);

        assertThat(usuarioSalvo.getId()).isNotNull();
        assertThat(usuarioSalvo.getNome()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("2. Deve buscar usuário por ID com sucesso")
    void deveBuscarUsuarioPorId() {
        Usuario usuario = new Usuario(
            null,
            "Maria Oliveira",
            "maria.oliveira@gsuite.iff.edu.br",
            "senha123",
            "20241002",
            "(22)99999-1111",
            5.0,
            TipoPerfil.MOTORISTA
        );
        Usuario usuarioSalvo = usuarioRepositorio.save(usuario);

        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findById(usuarioSalvo.getId());

        assertThat(usuarioEncontrado).isPresent();
        assertThat(usuarioEncontrado.get().getEmailInstitucional()).isEqualTo("maria.oliveira@gsuite.iff.edu.br");
    }

    @Test
    @DisplayName("3. Deve lançar exceção ao tentar salvar usuário com e-mail duplicado")
    void deveLancarExcecaoAoSalvarEmailDuplicado() {
        Usuario user1 = new Usuario(
            null,
            "Carlos",
            "duplicado@gsuite.iff.edu.br",
            "123",
            "20241003",
            "(22)99999-2222",
            5.0,
            TipoPerfil.PASSAGEIRO
        );
        usuarioRepositorio.saveAndFlush(user1);

        Usuario user2 = new Usuario(
            null,
            "Outro Carlos",
            "duplicado@gsuite.iff.edu.br", 
            "456",
            "20241004",
            "(22)99999-3333",
            5.0,
            TipoPerfil.PASSAGEIRO
        );

        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepositorio.saveAndFlush(user2);
        });
    }
}