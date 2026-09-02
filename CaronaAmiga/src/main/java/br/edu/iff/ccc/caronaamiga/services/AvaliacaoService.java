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
        Usuario avaliador = this.usuarioRepositorio.findById(dto.getAvaliadorId()).orElse(null);
        Usuario avaliado = this.usuarioRepositorio.findById(dto.getAvaliadoId()).orElse(null);
        Carona carona = this.caronaRepositorio.findById(dto.getCaronaId()).orElse(null);
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
            this.avaliacaoRepositorio.save(avaliacao);
            recalcularReputacao(avaliado.getId());
            return avaliacao;
        }
        return null;
    }

    private void recalcularReputacao(Long usuarioId) {
        List<Avaliacao> avaliacoes = this.avaliacaoRepositorio.findByAvaliadoId(usuarioId);
        if (!avaliacoes.isEmpty()) {
            double soma = 0.0;
            for (Avaliacao a : avaliacoes) {
                soma += a.getNota();
            }
            
            double media = soma / avaliacoes.size();
            Usuario usuario = this.usuarioRepositorio.findById(usuarioId).orElse(null);
            if (usuario != null) {
                usuario.setReputacao(Math.round(media * 10.0) / 10.0);
                this.usuarioRepositorio.save(usuario);
            }
        }
    }

    public List<Avaliacao> listarPorAvaliado(Long usuarioId) {
        return this.avaliacaoRepositorio.findByAvaliadoId(usuarioId);
    }

    public List<Avaliacao> listarTodas() {
        return this.avaliacaoRepositorio.findAll();
    }

    public void deletar(Long id) {
        this.avaliacaoRepositorio.deleteById(id);
    }
}