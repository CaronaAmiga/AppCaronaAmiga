package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicial")
public class MainViewController {

    @GetMapping
    public String getPaginaInicial(Model model) {
            return "inicial";
    }
}
