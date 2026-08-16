package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.caronaamiga.dto.UsuarioDTO;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.services.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    public UsuarioViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", this.usuarioService.listar());
        return "usuarios";
    }

    @GetMapping("/novo")
    public String formularioNovoUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        model.addAttribute("isEdicao", false);
        return "usuarioForm";
    }

    @PostMapping
    public String salvarUsuario(UsuarioDTO usuarioDTO) {
        this.usuarioService.cadastrar(usuarioDTO);
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}")
    public String detalhesUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = this.usuarioService.buscarPorId(id);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarioDetalhes";
    }

    @GetMapping("/{id}/editar")
    public String formularioEdicao(@PathVariable Long id, Model model) {
        Usuario usuario = this.usuarioService.buscarPorId(id);
        if (usuario == null) {
            return "redirect:/usuarios";
        }

        UsuarioDTO dto = new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmailInstitucional(),
            usuario.getSenhaHash(),
            usuario.getMatricula(),
            usuario.getTelefone(),
            usuario.getPerfilAtivo()
        );

        model.addAttribute("usuario", dto);
        model.addAttribute("usuarioId", id);
        model.addAttribute("isEdicao", true);
        return "usuarioForm";
    }

    @PostMapping("/{id}/editar")
    public String atualizarUsuario(@PathVariable Long id, UsuarioDTO usuarioDTO) {
        this.usuarioService.atualizar(id, usuarioDTO);
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/excluir")
    public String excluirUsuario(@PathVariable Long id) {
        this.usuarioService.deletar(id);
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/alternar-perfil")
    public String alternarPerfil(@PathVariable Long id) {
        this.usuarioService.alternarPerfil(id);
        return "redirect:/usuarios/" + id;
    }
}