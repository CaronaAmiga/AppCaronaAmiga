package br.edu.iff.ccc.caronaamiga.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.VeiculoDTO;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;
import br.edu.iff.ccc.caronaamiga.repositories.UsuarioRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.VeiculoRepositorio;


@Service
public class VeiculoService {
    private final VeiculoRepositorio veiculoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    
    public VeiculoService(VeiculoRepositorio veiculoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.veiculoRepositorio = veiculoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void criarVeiculo(VeiculoDTO dto) {
        Usuario motorista = null;
        if(dto.getMotoristaId() != null){
            motorista = this.usuarioRepositorio.findById(dto.getMotoristaId()).orElse(null);
        }

        Veiculo novoVeiculo = new Veiculo(
            dto.getId(),
            dto.getModelo(),
            dto.getCor(),
            dto.getPlaca(),
            dto.getQuantidadeVagas(),
            motorista
        );

        this.veiculoRepositorio.save(novoVeiculo);
    } 

    public List<Veiculo> listarVeiculo() {
        return this.veiculoRepositorio.findAll();
    }

    public Veiculo buscarPorId(Long id) {
        return this.veiculoRepositorio.findById(id).orElse(null);
    }

    public List<Veiculo> listarPorMotoristaId(Long motoristaId){
        return this.veiculoRepositorio.findByMotoristaId(motoristaId);
    }

    public void atualizarVeiculo(Long id, VeiculoDTO dto) {
        Veiculo veiculo = buscarPorId(id);
        if (veiculo != null) {
            veiculo.atualizarDados(dto.getModelo(), dto.getCor(), dto.getPlaca(), dto.getQuantidadeVagas());
            if (dto.getMotoristaId() != null) {
                Usuario motorista = this.usuarioRepositorio.findById(dto.getMotoristaId()).orElse(null);
                veiculo.setMotorista(motorista);
            }
            this.veiculoRepositorio.save(veiculo);
        }
    }

    public void deletarVeiculo(Long id) {
        this.veiculoRepositorio.deleteById(id);
    }
}