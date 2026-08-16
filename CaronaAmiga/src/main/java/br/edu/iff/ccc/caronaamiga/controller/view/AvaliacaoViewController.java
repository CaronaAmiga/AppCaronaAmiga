package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.caronaamiga.dto.AvaliacaoDTO;
import br.edu.iff.ccc.caronaamiga.services.AvaliacaoService;
import br.edu.iff.ccc.caronaamiga.services.CaronaService;
import br.edu.iff.ccc.caronaamiga.services.UsuarioService;

@Controller
@RequestMapping("/avaliacoes")
public class AvaliacaoViewController {

    private final AvaliacaoService avaliacaoService;
    private final UsuarioService usuarioService;
    private final CaronaService caronaService;

    public AvaliacaoViewController(AvaliacaoService avaliacaoService, UsuarioService usuarioService, CaronaService caronaService) {
        this.avaliacaoService = avaliacaoService;
        this.usuarioService = usuarioService;
        this.caronaService = caronaService;
    }

    @GetMapping
    public String listarAvaliacoes(Model model) {
        model.addAttribute("avaliacoes", this.avaliacaoService.listarTodas());
        return "avaliacoes";
    }

    @GetMapping("/nova")
    public String formularioNovaAvaliacao(Model model) {
        model.addAttribute("avaliacao", new AvaliacaoDTO());
        model.addAttribute("usuarios", this.usuarioService.listar());
        model.addAttribute("caronas", this.caronaService.listarTodas());
        return "avaliacaoForm";
    }

    @PostMapping
    public String criarAvaliacao(AvaliacaoDTO avaliacaoDTO) {
        this.avaliacaoService.avaliar(avaliacaoDTO);
        return "redirect:/avaliacoes";
    }

    @PostMapping("/{id}/excluir")
    public String excluirAvaliacao(@PathVariable Long id) {
        this.avaliacaoService.deletar(id);
        return "redirect:/avaliacoes";
    }
}