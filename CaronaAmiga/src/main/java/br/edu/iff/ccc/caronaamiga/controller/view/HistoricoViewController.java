package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.caronaamiga.services.HistoricoViagemService;

@Controller
@RequestMapping("/historico")
public class HistoricoViewController {

    private final HistoricoViagemService historicoService;

    public HistoricoViewController(HistoricoViagemService historicoService) {
        this.historicoService = historicoService;
    }

    @GetMapping
    public String listarHistorico(Model model) {
        model.addAttribute("historicos", this.historicoService.listarTodos());
        return "historico";
    }

    @PostMapping("/{id}/excluir")
    public String excluirHistorico(@PathVariable Long id) {
        this.historicoService.deletar(id);
        return "redirect:/historico";
    }
}