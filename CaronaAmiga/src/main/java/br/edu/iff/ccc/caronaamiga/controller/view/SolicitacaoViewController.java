package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoViewController {

    @GetMapping("/pendentes")
    public String getPainelSolicitacoes(Model model) {
        return "solicitacao/painel";
    }
}