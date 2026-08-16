package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;

@Repository
public class VeiculoRepositorio {
    private final List<Veiculo> veiculos = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);
    private final UsuarioRepositorio usuarioRepositorio;

    public VeiculoRepositorio(UsuarioRepositorio usuarioRepositorio){
        this.usuarioRepositorio = usuarioRepositorio;

        Usuario carlos = this.usuarioRepositorio.buscarPorId(1L);

        Veiculo veiculo1 = new Veiculo(
           contadorId.getAndIncrement(),
            "Fiat Uno",
            "Cinza",
            "ABC-1234",
            4,
            carlos
        );

        this.veiculos.add(veiculo1);
    }

    public void salvar(Veiculo veiculo){
        if(veiculo.getId() == null || veiculo.getId() <= 0){
            veiculo.setId(contadorId.getAndIncrement());
            this.veiculos.add(veiculo);
        }

        else{
            atualizar(veiculo);
        }
    }

    public List<Veiculo> listar(){
        return new ArrayList<>(this.veiculos);
    }

    public List<Veiculo> listarPorMotoristaId(Long motoristaId){
        List<Veiculo> resultado = new ArrayList<>();

        if(motoristaId == null){
            return resultado;
        }
        
        for(Veiculo v : this.veiculos){
            if(v.getMotorista() != null && motoristaId.equals(v.getMotorista().getId())){
                resultado.add(v);
            }
        }

        return resultado;
    }

    public Veiculo buscarPorId(Long id){
        if (id == null) {
            return null;
        }

        for (Veiculo v : this.veiculos) {
            if (id.equals(v.getId())) {
                return v;
            }
        }
        
        return null;
    }

    public void atualizar(Veiculo veiculoAtualizado){
        for(int i = 0; i < this.veiculos.size(); i++){
            if(this.veiculos.get(i).getId().equals(veiculoAtualizado.getId())){
                this.veiculos.set(i, veiculoAtualizado);
                return;
            }
        }
    }

    public void deletar(Long id){
        for (int i = 0; i < this.veiculos.size(); i++) {
            if (this.veiculos.get(i).getId().equals(id)) {
                this.veiculos.remove(i);
                return;
            }
        }
    }
}