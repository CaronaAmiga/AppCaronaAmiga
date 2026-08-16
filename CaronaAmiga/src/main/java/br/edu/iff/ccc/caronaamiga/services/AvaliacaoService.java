package br.edu.iff.ccc.caronaamiga.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.AvaliacaoDTO;
import br.edu.iff.ccc.caronaamiga.entities.Avaliacao;
import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.repositories.AvaliacaoRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.CaronaRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.UsuarioRepositorio;

@Service
public class AvaliacaoService{
    private final AvaliacaoRepositorio avaliacaoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CaronaRepositorio caronaRepositorio;

    public AvaliacaoService(AvaliacaoRepositorio avaliacaoRepositorio, UsuarioRepositorio usuarioRepositorio, CaronaRepositorio caronaRepositorio) {
        this.avaliacaoRepositorio = avaliacaoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.caronaRepositorio = caronaRepositorio;
    }

    public Avaliacao avaliar(AvaliacaoDTO dto) {
        Usuario avaliador = this.usuarioRepositorio.buscarPorId(dto.getAvaliadorId());
        Usuario avaliado = this.usuarioRepositorio.buscarPorId(dto.getAvaliadoId());
        Carona carona = this.caronaRepositorio.buscarPorId(dto.getCaronaId());
        if (avaliador != null && avaliado != null) {
            Avaliacao avaliacao = new Avaliacao(
                0L,
                dto.getNota(),
                dto.getComentario(),
                LocalDateTime.now(),
                avaliador,
                avaliado,
                carona
            );
            this.avaliacaoRepositorio.salvar(avaliacao);
            // Recalcula média de reputação do usuário avaliado
            recalcularReputacao(avaliado.getId());
            return avaliacao;
        }
        return null;
    }

    private void recalcularReputacao(Long usuarioId) {
        List<Avaliacao> avaliacoes = this.avaliacaoRepositorio.listarPorAvaliadoId(usuarioId);
        if (!avaliacoes.isEmpty()) {
            double soma = 0.0;
            for (Avaliacao a : avaliacoes) {
                soma += a.getNota();
            }
            
            double media = soma / avaliacoes.size();
            Usuario usuario = this.usuarioRepositorio.buscarPorId(usuarioId);
            if (usuario != null) {
                usuario.setReputacao(Math.round(media * 10.0) / 10.0);
                this.usuarioRepositorio.atualizar(usuario);
            }
        }
    }

    public List<Avaliacao> listarPorAvaliado(Long usuarioId) {
        return this.avaliacaoRepositorio.listarPorAvaliadoId(usuarioId);
    }

    public List<Avaliacao> listarTodas() {
        return this.avaliacaoRepositorio.listar();
    }

    public void deletar(Long id) {
        this.avaliacaoRepositorio.deletar(id);
    }
}