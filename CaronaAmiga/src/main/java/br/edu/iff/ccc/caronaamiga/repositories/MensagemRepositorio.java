package br.edu.iff.ccc.caronaamiga.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Mensagem;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;

@Repository
public class MensagemRepositorio {

    private final List<Mensagem> mensagens = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public MensagemRepositorio(UsuarioRepositorio usuarioRepositorio) {
        Usuario carlos = usuarioRepositorio.buscarPorId(1L);
        Usuario fernanda = usuarioRepositorio.buscarPorId(2L);

        if (carlos != null && fernanda != null) {
            Mensagem m1 = new Mensagem(
                contadorId.getAndIncrement(),
                "Olá Carlos, vou esperar no portão principal do campus!",
                LocalDateTime.now().minusHours(1),
                fernanda,
                carlos
            );
            this.mensagens.add(m1);
        }
    }

    public void salvar(Mensagem mensagem) {
        if (mensagem.getId() <= 0) {
            mensagem.setId(contadorId.getAndIncrement());
            this.mensagens.add(mensagem);
        } 
        
        else {
            atualizar(mensagem);
        }
    }

    public List<Mensagem> listar() {
        return new ArrayList<>(this.mensagens);
    }

    public Mensagem buscarPorId(Long id) {
        if (id == null) {
            return null;
        }

        for (Mensagem m : this.mensagens) {
            if (m.getId() == id) {
                return m;
            }
        }

        return null;
    }

    public List<Mensagem> listarConversa(Long idUsuario1, Long idUsuario2) {
        List<Mensagem> resultado = new ArrayList<>();
        if (idUsuario1 == null || idUsuario2 == null) {
            return resultado;
        }

        for (Mensagem m : this.mensagens) {
            if (m.getRemetente() != null && m.getDestinatario() != null) {
                boolean caso1 = idUsuario1.equals(m.getRemetente().getId()) && idUsuario2.equals(m.getDestinatario().getId());
                boolean caso2 = idUsuario2.equals(m.getRemetente().getId()) && idUsuario1.equals(m.getDestinatario().getId());

                if (caso1 || caso2) {
                    resultado.add(m);
                }
            }
        }
        
        return resultado;
    }

    public void atualizar(Mensagem atualizada) {
        for (int i = 0; i < this.mensagens.size(); i++) {
            if (this.mensagens.get(i).getId() == atualizada.getId()) {
                this.mensagens.set(i, atualizada);
                return;
            }
        }
    }

    public void deletar(Long id) {
        for (int i = 0; i < this.mensagens.size(); i++) {
            if (this.mensagens.get(i).getId() == id) {
                this.mensagens.remove(i);
                return;
            }
        }
    }
}