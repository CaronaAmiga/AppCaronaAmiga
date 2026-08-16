package br.edu.iff.ccc.caronaamiga.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.StatusCarona;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;

@Repository
public class CaronaRepositorio {
    private final List<Carona> caronas = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);
    private final UsuarioRepositorio usuarioRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;

    public CaronaRepositorio(UsuarioRepositorio usuarioRepositorio, VeiculoRepositorio veiculoRepositorio){
        this.usuarioRepositorio = usuarioRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;

        Usuario carlos = this.usuarioRepositorio.buscarPorId(1L);
        Veiculo uno = veiculoRepositorio.buscarPorId(1L);

        Carona carona1 = new Carona(
            contadorId.getAndIncrement(),
            "Cidade da Criança",
            "Campus IFF Centro",
            LocalDate.now().plusDays(1),
            LocalTime.of(7,30),
            5.0,
            3,
            15.0,
            StatusCarona.AGENDADA,
            carlos,
            uno
        );

        this.caronas.add(carona1);
    }

    public void salvar(Carona carona){
        if(carona.getId() == null || carona.getId() <= 0){
            carona.setId(contadorId.getAndIncrement());
            this.caronas.add(carona);
        }

        else{
            atualizar(carona);
        }
    }

    public List<Carona> listar(){
        return new ArrayList<>(this.caronas);
    }
    
    public Carona buscarPorId(Long id) {
        if (id == null) return null;
        for (Carona c : this.caronas) {
            if (id.equals(c.getId())) {
                return c;
            }
        }
        return null;
    }

    public List<Carona> buscarPorDestino(String destino){
        List<Carona> resultado = new ArrayList<>();

        if (destino == null || destino.isBlank()) {
            return listar();
        }

        for (Carona c : this.caronas) {
            if (c.getDestino() != null && c.getDestino().toLowerCase().contains(destino.toLowerCase())) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    public List<Carona> listarPorMotoristaId(Long motoristaId) {
        List<Carona> resultado = new ArrayList<>();
        if (motoristaId == null) return resultado;
        for (Carona c : this.caronas) {
            if (c.getMotorista() != null && motoristaId.equals(c.getMotorista().getId())) {
                resultado.add(c);
            }
        }
        return resultado;
    }


    public void atualizar(Carona caronaAtualizada){
        for(int i = 0; i < this.caronas.size(); i++){
            if(this.caronas.get(i).getId().equals(caronaAtualizada.getId())){
                this.caronas.set(i, caronaAtualizada);
                return;
            }
        }
    }

    public void deletar(Long id){
        for (int i = 0; i < this.caronas.size(); i++) {
            if (this.caronas.get(i).getId().equals(id)) {
                this.caronas.remove(i);
                return;
            }
        }
    }
}
