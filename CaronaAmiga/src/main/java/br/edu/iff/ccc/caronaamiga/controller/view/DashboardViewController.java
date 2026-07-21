package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardViewController {

    @GetMapping
    public String getDashboard(Model model) {
        // TODO: Injetar usuário e listas de caronas no model futuramente
        return "dashboard"; // ou "dashboard/index" se estiver em subpasta
    }
}