package br.edu.iff.ccc.caronaamiga.controller.rest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.iff.ccc.caronaamiga.dto.CaronaDTO;
import br.edu.iff.ccc.caronaamiga.dto.UsuarioDTO;
import br.edu.iff.ccc.caronaamiga.dto.VeiculoDTO;
import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.repositories.UsuarioRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.VeiculoRepositorio;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestApiValidationAndExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Test
    @DisplayName("TR07: Payload de usuário inválido deve retornar 400 Bad Request no padrão RFC 9457 com 'invalid_params'")
    void testCadastrarUsuarioInvalidoRetorna400ProblemDetail() throws Exception {
        UsuarioDTO dtoInvalido = new UsuarioDTO();
        dtoInvalido.setNome(""); // @NotBlank
        dtoInvalido.setEmailInstitucional("email-invalido"); // @Email
        dtoInvalido.setMatricula("123"); // menor que 12 caracteres (@Size)
        dtoInvalido.setTelefone(""); // @NotBlank

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.title").value("Erro de Validação"))
                .andExpect(jsonPath("$.type").value("https://api.caronaamiga.iff.edu.br/erros/dados-invalidos"))
                .andExpect(jsonPath("$.detail").exists())
                .andExpect(jsonPath("$.invalid_params").isArray())
                .andExpect(jsonPath("$.invalid_params[*].name").value(hasItems("nome", "emailInstitucional", "matricula", "telefone")));
    }

    @Test
    @DisplayName("TR07: Buscar usuário com ID inexistente deve retornar 404 Not Found no padrão RFC 9457")
    void testBuscarUsuarioInexistenteRetorna404ProblemDetail() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Recurso Não Encontrado"))
                .andExpect(jsonPath("$.type").value("https://api.caronaamiga.iff.edu.br/erros/recurso-nao-encontrado"))
                .andExpect(jsonPath("$.detail").value(containsString("99999")));
    }

    @Test
    @DisplayName("TR07: Cadastrar usuário válido deve retornar 201 Created com cabeçalho Location e sem expor senhaHash")
    void testCadastrarUsuarioValidoRetorna201Created() throws Exception {
        UsuarioDTO dtoValido = new UsuarioDTO(
                null,
                "Lucas Aluno",
                "lucas.aluno@iff.edu.br",
                "SenhaSegura@123",
                "202410019287", // 12 caracteres
                "(22) 99888-1122",
                TipoPerfil.PASSAGEIRO
        );

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Lucas Aluno"))
                .andExpect(jsonPath("$.matricula").value("202410019287"))
                .andExpect(jsonPath("$.senhaHash").doesNotExist()); // Proteção de senha
    }

    @Test
    @DisplayName("TR07: Excluir usuário existente deve retornar 204 No Content")
    void testExcluirUsuarioRetorna204NoContent() throws Exception {
        Usuario usuario = usuarioRepositorio.save(new Usuario(
                null, "Usuario Del", "del@iff.edu.br", "123", "202410019999", "(22)999", 5.0, TipoPerfil.PASSAGEIRO
        ));

        mockMvc.perform(delete("/api/v1/usuarios/" + usuario.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("TR07: Veículo com placa inválida deve retornar 400 Bad Request com 'invalid_params'")
    void testCadastrarVeiculoPlacaInvalidaRetorna400() throws Exception {
        VeiculoDTO dto = new VeiculoDTO();
        dto.setModelo("Palio");
        dto.setCor("Azul");
        dto.setPlaca("PLACA_INVALIDA"); // Viola regex de placa
        dto.setQuantidadeVagas(4);
        dto.setMotoristaId(1L);

        mockMvc.perform(post("/api/v1/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.invalid_params[*].name").value(hasItems("placa")));
    }

    @Test
    @DisplayName("TR07: Carona com campos obrigatórios ausentes deve retornar 400 Bad Request com 'invalid_params'")
    void testCadastrarCaronaInvalidaRetorna400() throws Exception {
        CaronaDTO dto = new CaronaDTO();
        dto.setOrigem("");
        dto.setDestino("");

        mockMvc.perform(post("/api/v1/caronas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.invalid_params[*].name").value(hasItems("origem", "destino", "data", "horarioPartida")));
    }

    @Test
    @DisplayName("TR07: Contrato OpenAPI deve estar acessível e retornar 200 OK na rota /v3/api-docs")
    void testOpenApiDocsEndpointRetorna200() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.openapi").value(containsString("3.")))
                .andExpect(jsonPath("$.info.title").value("Carona Amiga API"))
                .andExpect(jsonPath("$.paths['/api/v1/usuarios']").exists())
                .andExpect(jsonPath("$.paths['/api/v1/veiculos']").exists())
                .andExpect(jsonPath("$.paths['/api/v1/caronas']").exists());
    }
}