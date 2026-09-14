package br.edu.iff.ccc.caronaamiga.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "br.edu.iff.ccc.caronaamiga.controller.view")
public class GlobalExceptionHandler {

    // 1. Captura quando um ID ou registro não é encontrado no banco (Status 404)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("titulo", "Recurso Não Encontrado");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("dataHora", LocalDateTime.now());
        return "error/404";
    }

    // 2. Captura violações de regra de negócio ou duplicidade (Status 400)
    @ExceptionHandler({RegraDeNegocioException.class, EntidadeDuplicadaException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRegraDeNegocio(Exception ex, Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("titulo", "Ação Não Permitida");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("dataHora", LocalDateTime.now());
        return "error/400";
    }

    // 3. Captura qualquer outro erro inesperado (Status 500)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleErroGeral(Exception ex, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("titulo", "Erro Interno no Servidor");
        model.addAttribute("mensagem", "Ocorreu um problema inesperado ao processar sua solicitação.");
        model.addAttribute("detalhe", ex.getMessage());
        model.addAttribute("dataHora", LocalDateTime.now());
        return "error/500";
    }
}