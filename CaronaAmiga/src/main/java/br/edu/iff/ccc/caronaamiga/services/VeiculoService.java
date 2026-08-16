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
            motorista = this.usuarioRepositorio.buscarPorId(dto.getMotoristaId());
        }

        Veiculo novoVeiculo = new Veiculo(
            dto.getId(),
            dto.getModelo(),
            dto.getCor(),
            dto.getPlaca(),
            dto.getQuantidadeVagas(),
            motorista
        );

        this.veiculoRepositorio.salvar(novoVeiculo);
    } 

    public List<Veiculo> listarVeiculo() {
        return this.veiculoRepositorio.listar();
    }

    public Veiculo buscarPorId(Long id) {
        return this.veiculoRepositorio.buscarPorId(id);
    }

    public List<Veiculo> listarPorMotoristaId(Long motoristaId){
        return this.veiculoRepositorio.listarPorMotoristaId(motoristaId);
    }

    public void atualizarVeiculo(Long id, VeiculoDTO dto) {
        Veiculo veiculo = this.veiculoRepositorio.buscarPorId(id);
        if (veiculo != null) {
            veiculo.atualizarDados(dto.getModelo(), dto.getCor(), dto.getPlaca(), dto.getQuantidadeVagas());
            if (dto.getMotoristaId() != null) {
                Usuario motorista = this.usuarioRepositorio.buscarPorId(dto.getMotoristaId());
                veiculo.setMotorista(motorista);
            }
            this.veiculoRepositorio.atualizar(veiculo);
        }
    }

    public void deletarVeiculo(Long id) {
        this.veiculoRepositorio.deletar(id);
    }
}