package br.edu.iff.ccc.caronaamiga.repositories;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.iff.ccc.caronaamiga.entities.Avaliacao;
import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;

@DataJpaTest
class AvaliacaoRepositorioTest {

    @Autowired
    private AvaliacaoRepositorio avaliacaoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    @DisplayName("Deve salvar avaliação e listar por avaliado ID")
    void deveSalvarEBuscarPorAvaliado() {
        Usuario avaliador = usuarioRepositorio.save(new Usuario(null, "Avaliador", "av1@gsuite.iff.edu.br", "123", "2024021", "(22)999", 5.0, TipoPerfil.PASSAGEIRO));
        Usuario avaliado = usuarioRepositorio.save(new Usuario(null, "Avaliado", "av2@gsuite.iff.edu.br", "123", "2024022", "(22)999", 5.0, TipoPerfil.MOTORISTA));

        avaliacaoRepositorio.save(new Avaliacao(null, 5, "Excelente motorista!", LocalDateTime.now(), avaliador, avaliado, null));

        List<Avaliacao> avaliacoes = avaliacaoRepositorio.findByAvaliadoId(avaliado.getId());

        assertThat(avaliacoes).hasSize(1);
        assertThat(avaliacoes.get(0).getNota()).isEqualTo(5);
    }
}