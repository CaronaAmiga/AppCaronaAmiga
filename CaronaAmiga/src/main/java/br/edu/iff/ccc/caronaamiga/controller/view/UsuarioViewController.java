package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    @GetMapping("/login")
    public String getPaginaLogin(Model model) {
        return "usuario/login";
    }

    @GetMapping("/cadastro")
    public String getFormularioCadastro(Model model) {
        return "usuario/formulario";
    }

    @GetMapping("/perfil")
    public String getPerfilUsuario(Model model) {
        return "usuario/perfil";
    }
}