package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/caronas")
public class CaronaViewController {

    @GetMapping
    public String getCaronas(Model model) {
        return "carona/busca"; 
    }

    @GetMapping("/{id}")
    public String getDetalhesCarona(@PathVariable Long id, Model model) {
        return "carona/detalhes"; 
    }

    @GetMapping("/nova")
    public String getFormularioNovaCarona(Model model) {
        return "carona/formulario";
    }
}