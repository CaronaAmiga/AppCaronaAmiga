package br.edu.iff.ccc.caronaamiga.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.SolicitacaoCaronaDTO;
import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.HistoricoViagem;
import br.edu.iff.ccc.caronaamiga.entities.SolicitacaoCarona;
import br.edu.iff.ccc.caronaamiga.entities.StatusSolicitacao;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.repositories.CaronaRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.HistoricoViagemRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.SolicitacaoCaronaRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.UsuarioRepositorio;

@Service
public class SolicitacaoCaronaService {
    private final SolicitacaoCaronaRepositorio solicitacaoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CaronaRepositorio caronaRepositorio;
    private final HistoricoViagemRepositorio historicoViagemRepositorio;

    public SolicitacaoCaronaService(SolicitacaoCaronaRepositorio solicitacaoRepositorio, UsuarioRepositorio usuarioRepositorio, CaronaRepositorio caronaRepositorio, HistoricoViagemRepositorio historicoViagemRepositorio) {
        this.solicitacaoRepositorio = solicitacaoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.caronaRepositorio = caronaRepositorio;
        this.historicoViagemRepositorio = historicoViagemRepositorio;
    }

    public SolicitacaoCarona solicitarVaga(SolicitacaoCaronaDTO dto) {
        Usuario passageiro = this.usuarioRepositorio.buscarPorId(dto.getPassageiroId());
        Carona carona = this.caronaRepositorio.buscarPorId(dto.getCaronaId());
        if (passageiro != null && carona != null && carona.getVagasDisponiveis() > 0) {
            SolicitacaoCarona solicitacao = new SolicitacaoCarona(
                0L,
                LocalDateTime.now(),
                StatusSolicitacao.PENDENTE,
                passageiro,
                carona
            );
            this.solicitacaoRepositorio.salvar(solicitacao);
            return solicitacao;
        }
        return null;
    }

    public void aprovar(Long solicitacaoId) {
        SolicitacaoCarona solicitacao = this.solicitacaoRepositorio.buscarPorId(solicitacaoId);
        if (solicitacao != null && solicitacao.getCarona() != null) {
            solicitacao.aprovar();
            this.caronaRepositorio.atualizar(solicitacao.getCarona());
            this.solicitacaoRepositorio.atualizar(solicitacao);
            HistoricoViagem histPassageiro = new HistoricoViagem(0L, LocalDateTime.now(), solicitacao.getPassageiro(), solicitacao.getCarona());
            this.historicoViagemRepositorio.salvar(histPassageiro);
        }
    }

    public void recusar(Long solicitacaoId) {
        SolicitacaoCarona solicitacao = this.solicitacaoRepositorio.buscarPorId(solicitacaoId);
        if (solicitacao != null) {
            solicitacao.recusar();
            this.solicitacaoRepositorio.atualizar(solicitacao);
        }
    }

    public void cancelar(Long solicitacaoId) {
        SolicitacaoCarona solicitacao = this.solicitacaoRepositorio.buscarPorId(solicitacaoId);
        if (solicitacao != null) {
            solicitacao.cancelar();
            this.solicitacaoRepositorio.atualizar(solicitacao);
        }
    }

    public List<SolicitacaoCarona> listarTodas() {
        return this.solicitacaoRepositorio.listar();
    }

    public List<SolicitacaoCarona> listarPorCarona(Long caronaId) {
        return this.solicitacaoRepositorio.listarPorCaronaId(caronaId);
    }

    public List<SolicitacaoCarona> listarPorPassageiro(Long passageiroId) {
        return this.solicitacaoRepositorio.listarPorPassageiroId(passageiroId);
    }

    public void deletar(Long id) {
        this.solicitacaoRepositorio.deletar(id);
    }
}
