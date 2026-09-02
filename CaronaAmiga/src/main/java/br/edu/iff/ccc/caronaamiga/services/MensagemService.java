package br.edu.iff.ccc.caronaamiga.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.MensagemDTO;
import br.edu.iff.ccc.caronaamiga.entities.Mensagem;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.repositories.MensagemRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.UsuarioRepositorio;

@Service
public class MensagemService {

    private final MensagemRepositorio mensagemRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public MensagemService(MensagemRepositorio mensagemRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.mensagemRepositorio = mensagemRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Mensagem enviarMensagem(MensagemDTO dto) {
        Usuario remetente = this.usuarioRepositorio.findById(dto.getRemetenteId()).orElse(null);
        Usuario destinatario = this.usuarioRepositorio.findById(dto.getDestinatarioId()).orElse(null);

        if (remetente != null && destinatario != null) {
            Mensagem mensagem = new Mensagem(
                0L,
                dto.getConteudo(),
                LocalDateTime.now(),
                remetente,
                destinatario
            );
            this.mensagemRepositorio.save(mensagem);
            return mensagem;
        }
        return null;
    }

    public List<Mensagem> obterConversa(Long idUsuario1, Long idUsuario2) {
        return this.mensagemRepositorio.findConversa(idUsuario1, idUsuario2);
    }

    public List<Mensagem> listarTodas() {
        return this.mensagemRepositorio.findAll();
    }

    public void deletar(Long id) {
        this.mensagemRepositorio.deleteById(id);
    }
}