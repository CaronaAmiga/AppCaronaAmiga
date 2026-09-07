package br.edu.iff.ccc.caronaamiga.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.VeiculoDTO;
import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;
import br.edu.iff.ccc.caronaamiga.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc.caronaamiga.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.caronaamiga.exception.RegraDeNegocioException;
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
        if (this.veiculoRepositorio.findByPlaca(dto.getPlaca()).isPresent()) {
            throw new EntidadeDuplicadaException("Já existe um veículo cadastrado com a placa " + dto.getPlaca());
        }

        Usuario motorista = null;
        
        if(dto.getMotoristaId() != null){
            motorista = this.usuarioRepositorio.findById(dto.getMotoristaId()).orElseThrow(() -> new RecursoNaoEncontradoException("Motorista não encontrado com o id: " + dto.getMotoristaId()));
            if (motorista.getPerfilAtivo() != TipoPerfil.MOTORISTA) {
                throw new RegraDeNegocioException("O proprietário do veículo deve ter perfil ativo de Motorista.");
            }
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
        return this.veiculoRepositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com o id: " + id));
    }

    public List<Veiculo> listarPorMotoristaId(Long motoristaId){
        return this.veiculoRepositorio.findByMotoristaId(motoristaId);
    }

    public void atualizarVeiculo(Long id, VeiculoDTO dto) {
        Veiculo veiculo = buscarPorId(id);

        veiculoRepositorio.findByPlaca(dto.getPlaca()).ifPresent(v -> {
            if (!v.getId().equals(id)) {
                throw new EntidadeDuplicadaException("Já existe outro veículo cadastrado com a placa " + dto.getPlaca());
            }
        });

        veiculo.atualizarDados(dto.getModelo(), dto.getCor(), dto.getPlaca(), dto.getQuantidadeVagas());

        if (dto.getMotoristaId() != null) {
            Usuario motorista = this.usuarioRepositorio.findById(dto.getMotoristaId()).orElseThrow(() -> new RecursoNaoEncontradoException("Motorista não encontrado com o id:" + dto.getMotoristaId()));

            if (motorista.getPerfilAtivo() != TipoPerfil.MOTORISTA) {
                throw new RegraDeNegocioException("O proprietário do veículo deve ter perfil ativo de Motorista.");
            }
            veiculo.setMotorista(motorista);
        }
    this.veiculoRepositorio.save(veiculo);
    }

    public void deletarVeiculo(Long id) {
        Veiculo veiculo = buscarPorId(id);
        this.veiculoRepositorio.delete(veiculo);
    }
}