package br.edu.iff.ccc.caronaamiga.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.UsuarioDTO;
import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.repositories.UsuarioRepositorio;

@Service
public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioService(UsuarioRepositorio usuarioRepositorio){
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Usuario cadastrar(UsuarioDTO dto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setEmailInstitucional(dto.getEmailInstitucional());
        novoUsuario.setSenhaHash(dto.getSenha());
        novoUsuario.setMatricula(dto.getMatricula());
        novoUsuario.setTelefone(dto.getTelefone());
        novoUsuario.setReputacao(5.0);
        if(dto.getPerfilAtivo() != null){
            novoUsuario.setPerfilAtivo(dto.getPerfilAtivo());
        }

        else{
            novoUsuario.setPerfilAtivo(TipoPerfil.PASSAGEIRO);
        }

        this.usuarioRepositorio.salvar(novoUsuario);
        return novoUsuario;
    }

    public List<Usuario> listar(){
        return this.usuarioRepositorio.listar();
    }

    public List<Usuario> listarMotoristas() {
        return this.usuarioRepositorio.listar().stream()
            .filter(u -> u.getPerfilAtivo() == TipoPerfil.MOTORISTA)
            .toList();
    }

    public Usuario buscarPorId(Long id){
        return this.usuarioRepositorio.buscarPorId(id);
    }

    public Usuario buscarPorEmail(String email){
        return this.usuarioRepositorio.buscarPorEmail(email);
    }

    public void atualizar(Long id, UsuarioDTO dto){
        Usuario usuarioExistente = this.usuarioRepositorio.buscarPorId(id);
        if (usuarioExistente != null) {
            usuarioExistente.setNome(dto.getNome());
            usuarioExistente.setEmailInstitucional(dto.getEmailInstitucional());
            if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
                usuarioExistente.setSenhaHash(dto.getSenha());
            }

            usuarioExistente.setMatricula(dto.getMatricula());
            usuarioExistente.setTelefone(dto.getTelefone());
            if (dto.getPerfilAtivo() != null) {
                usuarioExistente.setPerfilAtivo(dto.getPerfilAtivo());
            }

            this.usuarioRepositorio.atualizar(usuarioExistente);
        }
    }

    public void deletar(Long id){
        this.usuarioRepositorio.deletar(id);
    }

    public void alternarPerfil(Long id){
        Usuario usuario = this.usuarioRepositorio.buscarPorId(id);
        if(usuario != null){
            usuario.alternarPerfil();
            this.usuarioRepositorio.atualizar(usuario);
        }
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = this.usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null && usuario.getSenhaHash() != null && usuario.getSenhaHash().equals(senha)) {
            return usuario;
        }
        return null;
    }
}
