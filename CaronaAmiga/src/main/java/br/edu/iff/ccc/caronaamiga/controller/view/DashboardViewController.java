package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.iff.ccc.caronaamiga.services.CaronaService;
import br.edu.iff.ccc.caronaamiga.services.SolicitacaoCaronaService;
import br.edu.iff.ccc.caronaamiga.services.UsuarioService;
import br.edu.iff.ccc.caronaamiga.services.VeiculoService;

@Controller
public class DashboardViewController {

    private final UsuarioService usuarioService;
    private final VeiculoService veiculoService;
    private final CaronaService caronaService;
    private final SolicitacaoCaronaService solicitacaoService;

    public DashboardViewController(UsuarioService usuarioService, VeiculoService veiculoService, CaronaService caronaService, SolicitacaoCaronaService solicitacaoService) {
        this.usuarioService = usuarioService;
        this.veiculoService = veiculoService;
        this.caronaService = caronaService;
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping({"/", "/dashboard", "/inicial"})
    public String getDashboard(Model model) {
        model.addAttribute("totalUsuarios", this.usuarioService.listar().size());
        model.addAttribute("totalVeiculos", this.veiculoService.listarVeiculo().size());
        model.addAttribute("totalCaronas", this.caronaService.listarTodas().size());
        model.addAttribute("totalSolicitacoes", this.solicitacaoService.listarTodas().size());
        model.addAttribute("caronasRecentes", this.caronaService.listarTodas());
        return "inicial";
    }
}