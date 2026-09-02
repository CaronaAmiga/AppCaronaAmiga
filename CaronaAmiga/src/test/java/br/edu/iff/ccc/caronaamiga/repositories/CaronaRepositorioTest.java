package br.edu.iff.ccc.caronaamiga.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.StatusCarona;
import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;

@DataJpaTest
class CaronaRepositorioTest {

    @Autowired
    private CaronaRepositorio caronaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Test
    @DisplayName("Deve salvar e buscar carona por destino contendo texto")
    void deveSalvarEBuscarPorDestino() {
        Usuario motorista = usuarioRepositorio.save(new Usuario(null, "Carlos", "carlos@gsuite.iff.edu.br", "123", "2024001", "(22)999", 5.0, TipoPerfil.MOTORISTA));
        Veiculo veiculo = veiculoRepositorio.save(new Veiculo(null, "Palio", "Branco", "XYZ-9999", 4, motorista));

        Carona carona = new Carona(null, "Centro", "Campus IFF Guarus", LocalDate.now(), LocalTime.of(8, 0), 5.0, 3, 10.0, StatusCarona.AGENDADA, motorista, veiculo);
        caronaRepositorio.save(carona);

        List<Carona> resultado = caronaRepositorio.findByDestinoContainingIgnoreCase("guarus");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDestino()).contains("Campus IFF Guarus");
    }
}