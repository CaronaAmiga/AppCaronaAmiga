package br.edu.iff.ccc.caronaamiga.controller.view;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;
import br.edu.iff.ccc.caronaamiga.repositories.UsuarioRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.VeiculoRepositorio;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class ValidacaoEExcecaoMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Test
    @DisplayName("TR06: Submissão de usuário com dados inválidos deve recarregar 'usuarioForm' com erros no BindingResult")
    void testSubmeterUsuarioInvalidoRecarregaFormComErros() throws Exception {
        mockMvc.perform(post("/usuarios")
                .param("nome", "")
                .param("emailInstitucional", "email-invalido")
                .param("matricula", "")
                .param("telefone", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarioForm"))
                .andExpect(model().attributeHasFieldErrors("usuario", "nome"))
                .andExpect(model().attributeHasFieldErrors("usuario", "emailInstitucional"))
                .andExpect(model().attributeHasFieldErrors("usuario", "matricula"))
                .andExpect(model().attributeHasFieldErrors("usuario", "telefone"));
    }

    @Test
    @DisplayName("TR06: Submissão de veículo com dados inválidos deve recarregar 'veiculoForm' com erros no BindingResult")
    void testSubmeterVeiculoInvalidoRecarregaFormComErros() throws Exception {
        mockMvc.perform(post("/veiculos")
                .param("modelo", "")
                .param("cor", "")
                .param("placa", "PLACA-INVALIDA-MUITO-LONGA"))
                .andExpect(status().isOk())
                .andExpect(view().name("veiculoForm"))
                .andExpect(model().attributeHasFieldErrors("veiculo", "modelo"))
                .andExpect(model().attributeHasFieldErrors("veiculo", "cor"))
                .andExpect(model().attributeHasFieldErrors("veiculo", "placa"));
    }

    @Test
    @DisplayName("TR06: Submissão de carona com dados inválidos deve recarregar 'caronaForm' com erros no BindingResult")
    void testSubmeterCaronaInvalidaRecarregaFormComErros() throws Exception {
        mockMvc.perform(post("/caronas")
                .param("origem", "")
                .param("destino", "")
                .param("quilometragem", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("caronaForm"))
                .andExpect(model().attributeHasFieldErrors("carona", "origem"))
                .andExpect(model().attributeHasFieldErrors("carona", "destino"));
    }

    @Test
    @DisplayName("TR06: Acesso a usuário inexistente deve disparar RecursoNaoEncontradoException e renderizar 'error/404' com status 404")
    void testBuscarUsuarioInexistenteRetornaPagina404() throws Exception {
        mockMvc.perform(get("/usuarios/99999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"))
                .andExpect(model().attribute("status", 404))
                .andExpect(model().attributeExists("mensagem"));
    }

    @Test
    @DisplayName("TR06: Acesso a carona inexistente deve disparar RecursoNaoEncontradoException e renderizar 'error/404' com status 404")
    void testBuscarCaronaInexistenteRetornaPagina404() throws Exception {
        mockMvc.perform(get("/caronas/99999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"))
                .andExpect(model().attribute("status", 404))
                .andExpect(model().attributeExists("mensagem"));
    }

    @Test
    @DisplayName("TR06: Acesso a veículo inexistente deve disparar RecursoNaoEncontradoException e renderizar 'error/404' com status 404")
    void testBuscarVeiculoInexistenteRetornaPagina404() throws Exception {
        mockMvc.perform(get("/veiculos/99999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"))
                .andExpect(model().attribute("status", 404))
                .andExpect(model().attributeExists("mensagem"));
    }

    @Test
    @DisplayName("TR06: Violação de duplicidade ou regra de negócio deve disparar exceção personalizada e renderizar 'error/400' com status 400")
    void testViolarRegraDeNegocioRetornaPagina400() throws Exception {
        Usuario motorista = usuarioRepositorio.save(new Usuario(null, "Motorista Teste", "mot.teste@iff.edu.br", "1234", "202499111", "22988887777", 5.0, TipoPerfil.MOTORISTA));
        veiculoRepositorio.save(new Veiculo(null, "Fiat Uno", "Branco", "KXY-8899", 4, motorista));

        mockMvc.perform(post("/veiculos")
                .param("modelo", "Gol G5")
                .param("cor", "Prata")
                .param("placa", "KXY-8899")
                .param("quantidadeVagas", "4")
                .param("motoristaId", motorista.getId().toString()))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/400"))
                .andExpect(model().attribute("status", 400))
                .andExpect(model().attributeExists("mensagem"));
    }
}