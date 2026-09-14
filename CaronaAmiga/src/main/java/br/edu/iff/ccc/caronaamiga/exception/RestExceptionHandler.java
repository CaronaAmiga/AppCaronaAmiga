package br.edu.iff.ccc.caronaamiga.exception;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "br.edu.iff.ccc.caronaamiga.controller.rest")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // 1. Captura falhas de @Valid nos DTOs e injeta a propriedade "invalid_params" (RFC 9457)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."
        );
        problemDetail.setTitle("Erro de Validação");
        problemDetail.setType(URI.create("https://api.caronaamiga.iff.edu.br/erros/dados-invalidos"));

        // Mapeia cada campo com erro e sua respectiva mensagem
        List<Map<String, String>> invalidParams = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Map.of(
                        "name", fieldError.getField(),
                        "reason", fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Campo inválido"
                ))
                .toList();

        problemDetail.setProperty("invalid_params", invalidParams);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    // 2. Captura quando um recurso não é encontrado por ID (Status 404)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso Não Encontrado");
        problemDetail.setType(URI.create("https://api.caronaamiga.iff.edu.br/erros/recurso-nao-encontrado"));
        return problemDetail;
    }

    // 3. Captura violações de regras de negócio (Status 400)
    @ExceptionHandler(RegraDeNegocioException.class)
    public ProblemDetail handleRegraDeNegocio(RegraDeNegocioException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Regra de Negócio Violada");
        problemDetail.setType(URI.create("https://api.caronaamiga.iff.edu.br/erros/regra-de-negocio"));
        return problemDetail;
    }

    // 4. Captura tentativa de duplicidade, ex: placa ou e-mail já cadastrado (Status 409 Conflict)
    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ProblemDetail handleEntidadeDuplicada(EntidadeDuplicadaException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflito de Dados");
        problemDetail.setType(URI.create("https://api.caronaamiga.iff.edu.br/erros/conflito"));
        return problemDetail;
    }

    // 5. Captura argumentos ilegais genéricos (Status 400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Requisição Inválida");
        problemDetail.setType(URI.create("https://api.caronaamiga.iff.edu.br/erros/requisicao-invalida"));
        return problemDetail;
    }

    // 6. Fallback para erros inesperados: evita stack traces no console (Status 500)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleErroGeral(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno inesperado no servidor."
        );
        problemDetail.setTitle("Erro Interno no Servidor");
        problemDetail.setType(URI.create("https://api.caronaamiga.iff.edu.br/erros/erro-interno"));
        return problemDetail;
    }
}