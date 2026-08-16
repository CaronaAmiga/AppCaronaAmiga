package br.edu.iff.ccc.caronaamiga.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.SolicitacaoCarona;
import br.edu.iff.ccc.caronaamiga.entities.StatusSolicitacao;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;

@Repository
public class SolicitacaoCaronaRepositorio {

    private final List<SolicitacaoCarona> solicitacoes = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public SolicitacaoCaronaRepositorio(UsuarioRepositorio usuarioRepositorio, CaronaRepositorio caronaRepositorio) {
        Usuario fernanda = usuarioRepositorio.buscarPorId(2L);
        Carona carona1 = caronaRepositorio.buscarPorId(1L);

        if (fernanda != null && carona1 != null) {
            SolicitacaoCarona solicitacaoMock = new SolicitacaoCarona(
                contadorId.getAndIncrement(),
                LocalDateTime.now(),
                StatusSolicitacao.PENDENTE,
                fernanda,
                carona1
            );
            
            this.solicitacoes.add(solicitacaoMock);
        }
    }

    public void salvar(SolicitacaoCarona solicitacao) {
        if (solicitacao.getId() <= 0) {
            solicitacao.setId(contadorId.getAndIncrement());
            this.solicitacoes.add(solicitacao);
        } 
        
        else {
            atualizar(solicitacao);
        }
    }

    public List<SolicitacaoCarona> listar() {
        return new ArrayList<>(this.solicitacoes);
    }

    public SolicitacaoCarona buscarPorId(Long id) {
        if (id == null) {
            return null;
        }

        for (SolicitacaoCarona s : this.solicitacoes) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    public List<SolicitacaoCarona> listarPorCaronaId(Long caronaId) {
        List<SolicitacaoCarona> resultado = new ArrayList<>();
        if (caronaId == null) {
            return resultado;
        }

        for (SolicitacaoCarona s : this.solicitacoes) {
            if (s.getCarona() != null && caronaId.equals(s.getCarona().getId())) {
                resultado.add(s);
            }
        }

        return resultado;
    }

    public List<SolicitacaoCarona> listarPorPassageiroId(Long passageiroId) {
        List<SolicitacaoCarona> resultado = new ArrayList<>();
        if (passageiroId == null) {
            return resultado;
        }

        for (SolicitacaoCarona s : this.solicitacoes) {
            if (s.getPassageiro() != null && passageiroId.equals(s.getPassageiro().getId())) {
                resultado.add(s);
            }
        }
        
        return resultado;
    }

    public void atualizar(SolicitacaoCarona solicitacaoAtualizada) {
        for (int i = 0; i < this.solicitacoes.size(); i++) {
            if (this.solicitacoes.get(i).getId() == solicitacaoAtualizada.getId()) {
                this.solicitacoes.set(i, solicitacaoAtualizada);
                return;
            }
        }
    }

    public void deletar(Long id) {
        for (int i = 0; i < this.solicitacoes.size(); i++) {
            if (this.solicitacoes.get(i).getId() == id) {
                this.solicitacoes.remove(i);
                return;
            }
        }
    }
}