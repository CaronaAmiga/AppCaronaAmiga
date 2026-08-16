package br.edu.iff.ccc.caronaamiga.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.HistoricoViagem;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;

@Repository
public class HistoricoViagemRepositorio {

    private final List<HistoricoViagem> historicos = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public HistoricoViagemRepositorio(UsuarioRepositorio usuarioRepositorio, CaronaRepositorio caronaRepositorio) {
        Usuario carlos = usuarioRepositorio.buscarPorId(1L);
        Carona carona1 = caronaRepositorio.buscarPorId(1L);

        if (carlos != null && carona1 != null) {
            HistoricoViagem historico1 = new HistoricoViagem(
                contadorId.getAndIncrement(),
                LocalDateTime.now().minusDays(2),
                carlos,
                carona1
            );

            this.historicos.add(historico1);
        }
    }

    public void salvar(HistoricoViagem historico) {
        if (historico.getId() <= 0) {
            historico.setId(contadorId.getAndIncrement());
            this.historicos.add(historico);
        } 
        
        else {
            atualizar(historico);
        }
    }

    public List<HistoricoViagem> listar() {
        return new ArrayList<>(this.historicos);
    }

    public HistoricoViagem buscarPorId(Long id) {
        if (id == null) {
            return null;
        }

        for (HistoricoViagem h : this.historicos) {
            if (h.getId() == id) {
                return h;
            }
        }

        return null;
    }

    public List<HistoricoViagem> listarPorUsuarioId(Long usuarioId) {
        List<HistoricoViagem> resultado = new ArrayList<>();
        if (usuarioId == null) {
            return resultado;
        }

        for (HistoricoViagem h : this.historicos) {
            if (h.getUsuario() != null && usuarioId.equals(h.getUsuario().getId())) {
                resultado.add(h);
            }
        }
        
        return resultado;
    }

    public void atualizar(HistoricoViagem atualizado) {
        for (int i = 0; i < this.historicos.size(); i++) {
            if (this.historicos.get(i).getId() == atualizado.getId()) {
                this.historicos.set(i, atualizado);
                return;
            }
        }
    }

    public void deletar(Long id) {
        for (int i = 0; i < this.historicos.size(); i++) {
            if (this.historicos.get(i).getId() == id) {
                this.historicos.remove(i);
                return;
            }
        }
    }
}