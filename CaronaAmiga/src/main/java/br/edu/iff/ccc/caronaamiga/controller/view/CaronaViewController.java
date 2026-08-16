package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc.caronaamiga.dto.CaronaDTO;
import br.edu.iff.ccc.caronaamiga.dto.SolicitacaoCaronaDTO;
import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.services.CaronaService;
import br.edu.iff.ccc.caronaamiga.services.SolicitacaoCaronaService;
import br.edu.iff.ccc.caronaamiga.services.UsuarioService;
import br.edu.iff.ccc.caronaamiga.services.VeiculoService;

@Controller
@RequestMapping("/caronas")
public class CaronaViewController {

    private final CaronaService caronaService;
    private final UsuarioService usuarioService;
    private final VeiculoService veiculoService;
    private final SolicitacaoCaronaService solicitacaoCaronaService;

    public CaronaViewController(CaronaService caronaService, UsuarioService usuarioService, VeiculoService veiculoService, SolicitacaoCaronaService solicitacaoCaronaService) {
        this.caronaService = caronaService;
        this.usuarioService = usuarioService;
        this.veiculoService = veiculoService;
        this.solicitacaoCaronaService = solicitacaoCaronaService;
    }

    @GetMapping
    public String listarCaronas(@RequestParam(name = "destino", required = false) String destino, Model model) {
        if (destino != null && !destino.isBlank()) {
            model.addAttribute("caronas", this.caronaService.buscarPorDestino(destino));
        } else {
            model.addAttribute("caronas", this.caronaService.listarTodas());
        }
        model.addAttribute("destinoPesquisado", destino);
        return "caronas";
    }

    @GetMapping("/nova")
    public String formularioNovaCarona(Model model) {
        model.addAttribute("carona", new CaronaDTO());
        model.addAttribute("motoristas", this.usuarioService.listarMotoristas());
        model.addAttribute("veiculos", this.veiculoService.listarVeiculo());
        return "caronaForm";
    }

    @PostMapping
    public String criarCarona(CaronaDTO caronaDTO) {
        this.caronaService.criarCarona(caronaDTO);
        return "redirect:/caronas";
    }

    @GetMapping("/{id}")
    public String detalhesCarona(@PathVariable Long id, Model model) {
        Carona carona = this.caronaService.buscarPorId(id);
        if (carona == null) {
            return "redirect:/caronas";
        }
        model.addAttribute("carona", carona);
        model.addAttribute("solicitacoes", this.solicitacaoCaronaService.listarPorCarona(id));
        model.addAttribute("passageiros", this.usuarioService.listar());
        model.addAttribute("solicitacaoDTO", new SolicitacaoCaronaDTO());
        return "caronaDetalhes";
    }

    @PostMapping("/{id}/iniciar")
    public String iniciarCarona(@PathVariable Long id) {
        this.caronaService.iniciarCarona(id);
        return "redirect:/caronas/" + id;
    }

    @PostMapping("/{id}/concluir")
    public String concluirCarona(@PathVariable Long id) {
        this.caronaService.concluirCarona(id);
        return "redirect:/caronas/" + id;
    }

    @PostMapping("/{id}/cancelar")
    public String cancelarCarona(@PathVariable Long id) {
        this.caronaService.cancelarCarona(id);
        return "redirect:/caronas/" + id;
    }

    @PostMapping("/{id}/excluir")
    public String excluirCarona(@PathVariable Long id) {
        this.caronaService.deletarCarona(id);
        return "redirect:/caronas";
    }
}