package br.edu.iff.ccc.caronaamiga.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Avaliacao;
import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;

@Repository
public class AvaliacaoRepositorio {

    private final List<Avaliacao> avaliacoes = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public AvaliacaoRepositorio(UsuarioRepositorio usuarioRepositorio, CaronaRepositorio caronaRepositorio) {
        Usuario carlos = usuarioRepositorio.buscarPorId(1L);
        Usuario fernanda = usuarioRepositorio.buscarPorId(2L);
        Carona carona1 = caronaRepositorio.buscarPorId(1L);

        if (carlos != null && fernanda != null && carona1 != null) {
            Avaliacao avaliacao1 = new Avaliacao(
                contadorId.getAndIncrement(),
                5,
                "Motorista muito pontual e educado!",
                LocalDateTime.now().minusDays(1),
                fernanda,
                carlos,
                carona1
            );
            this.avaliacoes.add(avaliacao1);
        }
    }

    public void salvar(Avaliacao avaliacao) {
        if (avaliacao.getId() <= 0) {
            avaliacao.setId(contadorId.getAndIncrement());
            this.avaliacoes.add(avaliacao);
        } 
        
        else {
            atualizar(avaliacao);
        }
    }

    public List<Avaliacao> listar() {
        return new ArrayList<>(this.avaliacoes);
    }

    public Avaliacao buscarPorId(Long id) {
        if (id == null) {
            return null;
        }

        for (Avaliacao a : this.avaliacoes) {
            if (a.getId() == id) {
                return a;
            }
        }

        return null;
    }

    public List<Avaliacao> listarPorAvaliadoId(Long usuarioId) {
        List<Avaliacao> resultado = new ArrayList<>();
        if (usuarioId == null) {
            return resultado;
        }

        for (Avaliacao a : this.avaliacoes) {
            if (a.getAvaliado() != null && usuarioId.equals(a.getAvaliado().getId())) {
                resultado.add(a);
            }
        }
        
        return resultado;
    }

    public void atualizar(Avaliacao atualizada) {
        for (int i = 0; i < this.avaliacoes.size(); i++) {
            if (this.avaliacoes.get(i).getId() == atualizada.getId()) {
                this.avaliacoes.set(i, atualizada);
                return;
            }
        }
    }

    public void deletar(Long id) {
        for (int i = 0; i < this.avaliacoes.size(); i++) {
            if (this.avaliacoes.get(i).getId() == id) {
                this.avaliacoes.remove(i);
                return;
            }
        }
    }
}