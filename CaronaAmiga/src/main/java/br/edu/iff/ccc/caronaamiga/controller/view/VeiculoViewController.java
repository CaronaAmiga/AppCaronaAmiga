package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/veiculos")
public class VeiculoViewController {

    @GetMapping
    public String getListaVeiculos(Model model) {
        return "veiculo/lista";
    }

    @GetMapping("/novo")
    public String getFormularioVeiculo(Model model) {
        return "veiculo/formulario";
    }
}