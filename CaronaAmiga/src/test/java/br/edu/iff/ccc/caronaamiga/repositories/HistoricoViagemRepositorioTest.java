package br.edu.iff.ccc.caronaamiga.repositories;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.iff.ccc.caronaamiga.entities.HistoricoViagem;
import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;

@DataJpaTest
class HistoricoViagemRepositorioTest {

    @Autowired
    private HistoricoViagemRepositorio historicoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    @DisplayName("Deve salvar e listar histórico por usuário ID")
    void deveBuscarHistoricoPorUsuario() {
        Usuario usuario = usuarioRepositorio.save(new Usuario(null, "Viajante", "viajante@gsuite.iff.edu.br", "123", "2024041", "(22)999", 5.0, TipoPerfil.PASSAGEIRO));

        historicoRepositorio.save(new HistoricoViagem(null, LocalDateTime.now(), usuario, null));

        List<HistoricoViagem> historicos = historicoRepositorio.findByUsuarioId(usuario.getId());

        assertThat(historicos).hasSize(1);
    }
}