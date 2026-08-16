package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.caronaamiga.dto.MensagemDTO;
import br.edu.iff.ccc.caronaamiga.services.MensagemService;
import br.edu.iff.ccc.caronaamiga.services.UsuarioService;

@Controller
@RequestMapping("/mensagens")
public class MensagemViewController {

    private final MensagemService mensagemService;
    private final UsuarioService usuarioService;

    public MensagemViewController(MensagemService mensagemService, UsuarioService usuarioService) {
        this.mensagemService = mensagemService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarMensagens(Model model) {
        model.addAttribute("mensagens", this.mensagemService.listarTodas());
        model.addAttribute("mensagemDTO", new MensagemDTO());
        model.addAttribute("usuarios", this.usuarioService.listar());
        return "mensagens";
    }

    @PostMapping
    public String enviarMensagem(MensagemDTO mensagemDTO) {
        this.mensagemService.enviarMensagem(mensagemDTO);
        return "redirect:/mensagens";
    }

    @PostMapping("/{id}/excluir")
    public String excluirMensagem(@PathVariable Long id) {
        this.mensagemService.deletar(id);
        return "redirect:/mensagens";
    }
}