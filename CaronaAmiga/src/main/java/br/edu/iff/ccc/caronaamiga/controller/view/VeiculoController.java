package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.caronaamiga.dto.VeiculoDTO;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;
import br.edu.iff.ccc.caronaamiga.services.UsuarioService;
import br.edu.iff.ccc.caronaamiga.services.VeiculoService;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final UsuarioService usuarioService;

    public VeiculoController(VeiculoService veiculoService, UsuarioService usuarioService) {
        this.veiculoService = veiculoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarVeiculos(Model model) {
        model.addAttribute("veiculos", this.veiculoService.listarVeiculo());
        return "veiculos";
    }

    @GetMapping("/novo")
    public String formularioNovoVeiculo(Model model) {
        model.addAttribute("veiculo", new VeiculoDTO());
        model.addAttribute("usuarios", this.usuarioService.listar()); // Para o <select> do motorista
        model.addAttribute("isEdicao", false);
        return "veiculoForm";
    }

    @PostMapping
    public String criarVeiculo(VeiculoDTO veiculoDTO) {
        this.veiculoService.criarVeiculo(veiculoDTO);
        return "redirect:/veiculos";
    }

    @GetMapping("/{id}")
    public String detalhesVeiculo(@PathVariable Long id, Model model) {
        Veiculo veiculo = this.veiculoService.buscarPorId(id);
        if (veiculo == null) {
            return "redirect:/veiculos";
        }
        model.addAttribute("veiculo", veiculo);
        return "veiculoDetalhes";
    }

    @GetMapping("/{id}/editar")
    public String formularioEdicao(@PathVariable Long id, Model model) {
        Veiculo veiculo = this.veiculoService.buscarPorId(id);
        if (veiculo == null) {
            return "redirect:/veiculos";
        }

        Long motoristaId = veiculo.getMotorista() != null ? veiculo.getMotorista().getId() : null;
        VeiculoDTO dto = new VeiculoDTO(
            veiculo.getId(),
            veiculo.getModelo(),
            veiculo.getCor(),
            veiculo.getPlaca(),
            veiculo.getQuantidadeVagas(),
            motoristaId
        );

        model.addAttribute("veiculo", dto);
        model.addAttribute("veiculoId", id);
        model.addAttribute("usuarios", this.usuarioService.listar());
        model.addAttribute("isEdicao", true);
        return "veiculoForm";
    }

    @PostMapping("/{id}/editar")
    public String atualizarVeiculo(@PathVariable Long id, VeiculoDTO veiculoDTO) {
        this.veiculoService.atualizarVeiculo(id, veiculoDTO);
        return "redirect:/veiculos";
    }

    @PostMapping("/{id}/excluir")
    public String excluirVeiculo(@PathVariable Long id) {
        this.veiculoService.deletarVeiculo(id);
        return "redirect:/veiculos";
    }
}