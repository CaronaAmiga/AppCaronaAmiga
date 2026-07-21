package br.edu.iff.ccc.caronaamiga.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/acoes")
public class AcoesViewController {

    @PostMapping("/reservar/{idCarona}")
    public String solicitarReserva(@PathVariable Long idCarona, Model model) {
        // Lógica de reserva entra aqui na próxima fase
        return "redirect:/dashboard";
    }

    @GetMapping("/mensagem/{idCondutor}")
    public String abrirChatCondutor(@PathVariable Long idCondutor, Model model) {
        return "mensagem/chat";
    }
}