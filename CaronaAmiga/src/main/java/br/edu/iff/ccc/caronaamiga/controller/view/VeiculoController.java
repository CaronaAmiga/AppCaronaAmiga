package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iff.ccc.caronaamiga.dto.VeiculoRequest;
import br.edu.iff.ccc.caronaamiga.services.VeiculoUserCase;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoUserCase veiculoUserCase;

    VeiculoController(VeiculoUserCase veiculoUserCase){
        this.veiculoUserCase = veiculoUserCase;
    }

    @GetMapping("/novo")
    public String novoVeiculo(Model model) {
        VeiculoRequest novoVeiculo  = new VeiculoRequest();
        model.addAttribute("veiculo", novoVeiculo); 
        return "veiculoForm.html";
    }

    @PostMapping()
    public String criarVeiculo(VeiculoRequest veiculoRequest) {
        // Lógica para criar um produto
        this.veiculoUserCase.criarVeiculo(veiculoRequest);
        return "veiculos.html";
    }   

    @GetMapping
    public String listarVeiculos(Model model) {
        // Para a página veiculos.html funcionar, precisamos injetar a lista nela
        model.addAttribute("veiculos", this.veiculoUserCase.listarVeiculo());
        return "veiculos"; // Retorna templates/veiculos.html
    }
}