package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;

@DataJpaTest
class VeiculoRepositorioTest {

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    @DisplayName("Deve salvar veículo com sucesso atrelado a um motorista")
    void deveSalvarVeiculo() {
        Usuario motorista = usuarioRepositorio.save(new Usuario(null, "Motorista Teste", "mot@gsuite.iff.edu.br", "123", "2024991", "(22)999", 5.0, TipoPerfil.MOTORISTA));
        
        Veiculo veiculo = new Veiculo(null, "Gol 1.0", "Preto", "KLA-9876", 4, motorista);
        Veiculo salvo = veiculoRepositorio.save(veiculo);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getPlaca()).isEqualTo("KLA-9876");
    }

    @Test
    @DisplayName("Deve buscar veículos por ID do motorista")
    void deveBuscarPorMotoristaId() {
        Usuario motorista = usuarioRepositorio.save(new Usuario(null, "Motorista Teste 2", "mot2@gsuite.iff.edu.br", "123", "2024992", "(22)999", 5.0, TipoPerfil.MOTORISTA));
        veiculoRepositorio.save(new Veiculo(null, "Uno", "Cinza", "ABC-1111", 4, motorista));

        List<Veiculo> veiculos = veiculoRepositorio.findByMotoristaId(motorista.getId());

        assertThat(veiculos).hasSize(1);
        assertThat(veiculos.get(0).getModelo()).isEqualTo("Uno");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar veículo com placa duplicada")
    void deveLancarExcecaoPlacaDuplicada() {
        Usuario motorista = usuarioRepositorio.save(new Usuario(null, "Motorista Teste 3", "mot3@gsuite.iff.edu.br", "123", "2024993", "(22)999", 5.0, TipoPerfil.MOTORISTA));

        veiculoRepositorio.saveAndFlush(new Veiculo(null, "Gol", "Preto", "ABC-0000", 4, motorista));

        Veiculo v2 = new Veiculo(null, "Civic", "Prata", "ABC-0000", 4, motorista); // Placa duplicada

        assertThrows(DataIntegrityViolationException.class, () -> {
            veiculoRepositorio.saveAndFlush(v2);
        });
    }
}