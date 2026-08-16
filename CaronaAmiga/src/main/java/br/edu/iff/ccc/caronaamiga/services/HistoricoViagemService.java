package br.edu.iff.ccc.caronaamiga.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.entities.HistoricoViagem;
import br.edu.iff.ccc.caronaamiga.repositories.HistoricoViagemRepositorio;

@Service
public class HistoricoViagemService {

    private final HistoricoViagemRepositorio historicoRepositorio;

    public HistoricoViagemService(HistoricoViagemRepositorio historicoRepositorio) {
        this.historicoRepositorio = historicoRepositorio;
    }

    public List<HistoricoViagem> listarPorUsuario(Long usuarioId) {
        return this.historicoRepositorio.listarPorUsuarioId(usuarioId);
    }

    public List<HistoricoViagem> listarTodos() {
        return this.historicoRepositorio.listar();
    }

    public void deletar(Long id) {
        this.historicoRepositorio.deletar(id);
    }
}