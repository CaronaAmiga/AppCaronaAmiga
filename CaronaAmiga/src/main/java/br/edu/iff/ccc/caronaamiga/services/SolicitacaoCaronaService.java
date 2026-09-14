package br.edu.iff.ccc.caronaamiga.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.SolicitacaoCaronaDTO;
import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.HistoricoViagem;
import br.edu.iff.ccc.caronaamiga.entities.SolicitacaoCarona;
import br.edu.iff.ccc.caronaamiga.entities.StatusCarona;
import br.edu.iff.ccc.caronaamiga.entities.StatusSolicitacao;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.caronaamiga.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.caronaamiga.exception.RegraDeNegocioException;
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
        Usuario passageiro = this.usuarioRepositorio.findById(dto.getPassageiroId()).orElseThrow(() -> new RecursoNaoEncontradoException("Passageiro não encontrado com o id: " + dto.getPassageiroId()));

        Carona carona = this.caronaRepositorio.findById(dto.getCaronaId()).orElseThrow(() -> new RecursoNaoEncontradoException("Carona não encontrada com o id: " + dto.getCaronaId()));

        // Regra 1: Motorista não pode solicitar vaga na própria carona
        if (carona.getMotorista() != null && carona.getMotorista().getId().equals(passageiro.getId())) {
            throw new RegraDeNegocioException("O motorista condutor não pode solicitar vaga na sua própria carona.");
        }

        // Regra 2: Carona precisa estar agendada
        if (carona.getStatus() != StatusCarona.AGENDADA) {
            throw new RegraDeNegocioException("Não é possível solicitar vaga em caronas que não estejam agendadas.");
        }

        // Regra 3: Precisa ter vagas livres
        if (carona.getVagasDisponiveis() <= 0) {
            throw new RegraDeNegocioException("Não há vagas disponíveis para esta carona.");
        }

        // Regra 4: Passageiro não pode pedir duas vezes a mesma carona
        boolean caronaJaSolicitada = this.solicitacaoRepositorio.findByCaronaId(carona.getId()).stream().anyMatch(s -> s.getPassageiro() != null 
                        && s.getPassageiro().getId().equals(passageiro.getId()) 
                        && (s.getStatus() == StatusSolicitacao.PENDENTE || s.getStatus() == StatusSolicitacao.ACEITA));
        if (caronaJaSolicitada) {
            throw new EntidadeDuplicadaException("Você já possui uma solicitação pendente ou aceita para esta carona.");
        }

        SolicitacaoCarona solicitacao = new SolicitacaoCarona(
            0L,
            LocalDateTime.now(),
            StatusSolicitacao.PENDENTE,
            passageiro,
            carona
        );
        this.solicitacaoRepositorio.save(solicitacao);
        return solicitacao;
    }

    public void aprovar(Long solicitacaoId) {
        SolicitacaoCarona solicitacao = this.solicitacaoRepositorio.findById(solicitacaoId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada com o id: " + solicitacaoId));

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE) {
            throw new RegraDeNegocioException("Apenas solicitações com status PENDENTE podem ser aprovadas.");
        }

        if (solicitacao.getCarona() == null || solicitacao.getCarona().getVagasDisponiveis() <= 0) {
            throw new RegraDeNegocioException("Não há vagas disponíveis nesta carona para aprovação.");
        }

        solicitacao.aprovar();
        this.caronaRepositorio.save(solicitacao.getCarona());
        this.solicitacaoRepositorio.save(solicitacao);
        HistoricoViagem histPassageiro = new HistoricoViagem(0L, LocalDateTime.now(), solicitacao.getPassageiro(), solicitacao.getCarona());
        this.historicoViagemRepositorio.save(histPassageiro);
    }

    public void recusar(Long solicitacaoId) {
        SolicitacaoCarona solicitacao = this.solicitacaoRepositorio.findById(solicitacaoId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada com o id: " + solicitacaoId));

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE) {
            throw new RegraDeNegocioException("Apenas solicitações com status PENDENTE podem ser recusadas.");
        }

        solicitacao.recusar();
        this.solicitacaoRepositorio.save(solicitacao);
    }

    public void cancelar(Long solicitacaoId) {
        SolicitacaoCarona solicitacao = this.solicitacaoRepositorio.findById(solicitacaoId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada com o id: " + solicitacaoId));

        if (solicitacao.getStatus() == StatusSolicitacao.CANCELADA) {
            throw new RegraDeNegocioException("A solicitação já se encontra cancelada.");
        }

        solicitacao.cancelar();
        this.solicitacaoRepositorio.save(solicitacao);
    }

    public void deletar(Long id) {
        SolicitacaoCarona solicitacao = this.solicitacaoRepositorio.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada com o id: " + id));
        this.solicitacaoRepositorio.delete(solicitacao);
    }

    public List<SolicitacaoCarona> listarTodas() {
        return this.solicitacaoRepositorio.findAll();
    }

    public List<SolicitacaoCarona> listarPorCarona(Long caronaId) {
        return this.solicitacaoRepositorio.findByCaronaId(caronaId);
    }

    public List<SolicitacaoCarona> listarPorPassageiro(Long passageiroId) {
        return this.solicitacaoRepositorio.findByPassageiroId(passageiroId);
    }

    public SolicitacaoCarona buscarPorId(Long id) {
    return this.solicitacaoRepositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada com o id: " + id));
}
}
