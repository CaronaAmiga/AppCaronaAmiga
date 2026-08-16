package br.edu.iff.ccc.caronaamiga.controller.view;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.services.UsuarioService;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class UsuarioSessaoControllerAdvice {

    private final UsuarioService usuarioService;

    public UsuarioSessaoControllerAdvice(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("usuarioLogado")
    public Usuario getUsuarioLogado(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        List<Usuario> todos = this.usuarioService.listar();
        
        if (usuario == null) {
            if (!todos.isEmpty()) {
                usuario = todos.get(0);
                session.setAttribute("usuarioLogado", usuario);
            }
        } else {
            // Mantém os dados sincronizados em caso de troca de perfil ou edição
            Usuario atualizado = this.usuarioService.buscarPorId(usuario.getId());
            if (atualizado != null) {
                usuario = atualizado;
                session.setAttribute("usuarioLogado", usuario);
            } else if (!todos.isEmpty()) {
                usuario = todos.get(0);
                session.setAttribute("usuarioLogado", usuario);
            }
        }
        return usuario;
    }

    @ModelAttribute("todosUsuariosSessao")
    public List<Usuario> getTodosUsuariosSessao() {
        return this.usuarioService.listar();
    }
}
