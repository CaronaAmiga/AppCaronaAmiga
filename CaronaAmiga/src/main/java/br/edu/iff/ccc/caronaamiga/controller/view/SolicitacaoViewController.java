package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.caronaamiga.dto.SolicitacaoCaronaDTO;
import br.edu.iff.ccc.caronaamiga.services.SolicitacaoCaronaService;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoViewController {

    private final SolicitacaoCaronaService solicitacaoCaronaService;

    public SolicitacaoViewController(SolicitacaoCaronaService solicitacaoCaronaService) {
        this.solicitacaoCaronaService = solicitacaoCaronaService;
    }

    @GetMapping
    public String listarSolicitacoes(Model model) {
        model.addAttribute("solicitacoes", this.solicitacaoCaronaService.listarTodas());
        return "solicitacoes";
    }

    @PostMapping
    public String solicitarVaga(SolicitacaoCaronaDTO solicitacaoDTO) {
        this.solicitacaoCaronaService.solicitarVaga(solicitacaoDTO);
        if (solicitacaoDTO.getCaronaId() != null) {
            return "redirect:/caronas/" + solicitacaoDTO.getCaronaId();
        }
        return "redirect:/solicitacoes";
    }

    @PostMapping("/{id}/aprovar")
    public String aprovarSolicitacao(@PathVariable Long id) {
        this.solicitacaoCaronaService.aprovar(id);
        return "redirect:/solicitacoes";
    }

    @PostMapping("/{id}/recusar")
    public String recusarSolicitacao(@PathVariable Long id) {
        this.solicitacaoCaronaService.recusar(id);
        return "redirect:/solicitacoes";
    }

    @PostMapping("/{id}/cancelar")
    public String cancelarSolicitacao(@PathVariable Long id) {
        this.solicitacaoCaronaService.cancelar(id);
        return "redirect:/solicitacoes";
    }

    @PostMapping("/{id}/excluir")
    public String excluirSolicitacao(@PathVariable Long id) {
        this.solicitacaoCaronaService.deletar(id);
        return "redirect:/solicitacoes";
    }
}