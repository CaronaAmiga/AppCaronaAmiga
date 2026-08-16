package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;

@Repository
public class UsuarioRepositorio {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public UsuarioRepositorio(){
        Usuario motorista = new Usuario(
            contadorId.getAndIncrement(), 
            "Carlos Silva", 
            "carlos.silva@gsuite.iff.edu.br", 
            "senha123", 
            "202411567898", 
            "(22)99999-1111", 
            5.0, 
            TipoPerfil.MOTORISTA
        );

        Usuario passageiro = new Usuario(
            contadorId.getAndIncrement(),
            "Fernanda Costa",
            "fernanda.costa@gsuite.iff.edu.br",
            "senha123",
            "202121948274",
            "(22)99876-8741",
            5.0,
            TipoPerfil.PASSAGEIRO
        );

        this.usuarios.add(motorista);
        this.usuarios.add(passageiro);
    }

    public void salvar(Usuario usuario){
        if(usuario.getId() == null || usuario.getId() <= 0){
            usuario.setId(contadorId.getAndIncrement());
            this.usuarios.add(usuario);
        }

        else{
            atualizar(usuario);
        }
    }

    public List<Usuario> listar(){
        return new ArrayList<>(this.usuarios);
    }

    public Usuario buscarPorId(Long id){
        if (id == null) {
            return null;
        }

        for (Usuario u : this.usuarios) {
            if (id.equals(u.getId())) {
                return u;
            }
        }

        return null;
    }

    public Usuario buscarPorEmail(String emailInstitucional){
        if (emailInstitucional == null) {
            return null;
        }

        for (Usuario u : this.usuarios) {
            if (u.getEmailInstitucional() != null && u.getEmailInstitucional().equalsIgnoreCase(emailInstitucional)) {
                return u;
            }
        }

        return null;
    }

    public void atualizar(Usuario usuarioAtualizado){
        for(int i = 0; i < this.usuarios.size(); i++){
            if(this.usuarios.get(i).getId().equals(usuarioAtualizado.getId())){
                this.usuarios.set(i, usuarioAtualizado);
                return;
            }
        }
    }

    public void deletar(Long id){
        for (int i = 0; i < this.usuarios.size(); i++) {
            if (this.usuarios.get(i).getId().equals(id)) {
                this.usuarios.remove(i);
                return;
            }
        }
    }
}
