package br.edu.iff.ccc.caronaamiga.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.iff.ccc.caronaamiga.entities.*;

@DataJpaTest
class SolicitacaoCaronaRepositorioTest {

    @Autowired
    private SolicitacaoCaronaRepositorio solicitacaoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;
    @Autowired
    private CaronaRepositorio caronaRepositorio;

    @Test
    @DisplayName("Deve salvar e listar solicitações por passageiro ID")
    void deveSalvarEBuscarPorPassageiro() {
        Usuario motorista = usuarioRepositorio.save(new Usuario(null, "Motorista", "mot.sol@gsuite.iff.edu.br", "123", "2024011", "(22)999", 5.0, TipoPerfil.MOTORISTA));
        Usuario passageiro = usuarioRepositorio.save(new Usuario(null, "Passageiro", "pas.sol@gsuite.iff.edu.br", "123", "2024012", "(22)999", 5.0, TipoPerfil.PASSAGEIRO));
        Veiculo veiculo = veiculoRepositorio.save(new Veiculo(null, "Fox", "Red", "SOL-1234", 4, motorista));
        Carona carona = caronaRepositorio.save(new Carona(null, "A", "B", LocalDate.now(), LocalTime.of(9,0), 4.0, 3, 5.0, StatusCarona.AGENDADA, motorista, veiculo));

        SolicitacaoCarona sol = solicitacaoRepositorio.save(new SolicitacaoCarona(null, LocalDateTime.now(), StatusSolicitacao.PENDENTE, passageiro, carona));

        List<SolicitacaoCarona> lista = solicitacaoRepositorio.findByPassageiroId(passageiro.getId());

        assertThat(lista).hasSize(1);
        assertThat(lista.get(0).getId()).isEqualTo(sol.getId());
    }
}