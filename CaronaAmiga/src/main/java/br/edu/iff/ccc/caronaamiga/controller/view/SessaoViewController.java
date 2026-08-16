package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.services.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class SessaoViewController {

    private final UsuarioService usuarioService;

    public SessaoViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/selecionar-usuario")
    public String selecionarUsuario(@RequestParam(name = "id", required = false) Long id,
                                    @RequestParam(name = "redirect", required = false, defaultValue = "/") String redirect,
                                    HttpSession session) {
        if (id != null) {
            Usuario usuario = this.usuarioService.buscarPorId(id);
            if (usuario != null) {
                session.setAttribute("usuarioLogado", usuario);
            }
        }
        if (redirect == null || redirect.isBlank() || redirect.startsWith("/selecionar-usuario")) {
            redirect = "/";
        }
        return "redirect:" + redirect;
    }
}
