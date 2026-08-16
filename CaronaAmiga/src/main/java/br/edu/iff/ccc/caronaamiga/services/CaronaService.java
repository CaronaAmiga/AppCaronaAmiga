package br.edu.iff.ccc.caronaamiga.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.CaronaDTO;
import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.HistoricoViagem;
import br.edu.iff.ccc.caronaamiga.entities.StatusCarona;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;
import br.edu.iff.ccc.caronaamiga.repositories.CaronaRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.HistoricoViagemRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.UsuarioRepositorio;
import br.edu.iff.ccc.caronaamiga.repositories.VeiculoRepositorio;

@Service
public class CaronaService{
    private final CaronaRepositorio caronaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;
    private final HistoricoViagemRepositorio historicoViagemRepositorio;

    public CaronaService(CaronaRepositorio caronaRepositorio, UsuarioRepositorio usuarioRepositorio, VeiculoRepositorio veiculoRepositorio, HistoricoViagemRepositorio historicoViagemRepositorio) {
        this.caronaRepositorio = caronaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;
        this.historicoViagemRepositorio = historicoViagemRepositorio;
    }

    public Carona criarCarona(CaronaDTO dto){
        Usuario motorista = this.usuarioRepositorio.buscarPorId(dto.getMotoristaId());
        if (motorista == null) {
            throw new IllegalArgumentException("Motorista não encontrado.");
        }
        if (motorista.getPerfilAtivo() != br.edu.iff.ccc.caronaamiga.entities.TipoPerfil.MOTORISTA) {
            throw new IllegalArgumentException("Apenas usuários com perfil ativo de Motorista podem oferecer caronas.");
        }

        Veiculo veiculo = this.veiculoRepositorio.buscarPorId(dto.getVeiculoId());
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }
        if (veiculo.getMotorista() == null || !veiculo.getMotorista().getId().equals(motorista.getId())) {
            throw new IllegalArgumentException("O veículo selecionado não pertence ao motorista condutor.");
        }

        Carona carona = new Carona();
        carona.setOrigem(dto.getOrigem());
        carona.setDestino(dto.getDestino());
        carona.setData(dto.getData());
        carona.setHorarioPartida(dto.getHorarioPartida());
        carona.setQuilometragem(dto.getQuilometragem());
        carona.setStatus(StatusCarona.AGENDADA);
        carona.setMotorista(motorista);
        carona.setVeiculo(veiculo);

        int vagas;
        if (dto.getVagasDisponiveis() > 0) {
            vagas = dto.getVagasDisponiveis();
        } 
        
        else if (veiculo != null && veiculo.getQuantidadeVagas() > 0) {
            vagas = veiculo.getQuantidadeVagas();
        } 
        
        else {
            vagas = 4; 
        }

        carona.setVagasDisponiveis(vagas);
        
        if (dto.getValorRateio() > 0) {
            carona.setValorRateio(dto.getValorRateio());
        } 
        
        else {
            carona.calcularRateio();
        }
        this.caronaRepositorio.salvar(carona);
        return carona;
    }

    public List<Carona> listarTodas(){
        return this.caronaRepositorio.listar();
    }

    public Carona buscarPorId(Long id){
        return this.caronaRepositorio.buscarPorId(id);
    }

    public List<Carona> buscarPorDestino(String destino) {
        return this.caronaRepositorio.buscarPorDestino(destino);
    }

    public List<Carona> listarPorMotorista(Long motoristaId) {
        return this.caronaRepositorio.listarPorMotoristaId(motoristaId);
    }

    public void iniciarCarona(Long id) {
        Carona carona = this.caronaRepositorio.buscarPorId(id);
        if (carona != null) {
            carona.iniciarCarona();
            this.caronaRepositorio.atualizar(carona);
        }
    }

    public void concluirCarona(Long id) {
        Carona carona = this.caronaRepositorio.buscarPorId(id);
        if (carona != null) {
            carona.concluirCarona();
            this.caronaRepositorio.atualizar(carona);
            if (carona.getMotorista() != null) {
                HistoricoViagem historico = new HistoricoViagem(0L, LocalDateTime.now(), carona.getMotorista(), carona);
                this.historicoViagemRepositorio.salvar(historico);
            }
        }
    }

    public void cancelarCarona(Long id) {
        Carona carona = this.caronaRepositorio.buscarPorId(id);
        if (carona != null) {
            carona.cancelarCarona();
            this.caronaRepositorio.atualizar(carona);
        }
    }

    public void deletarCarona(Long id) {
        this.caronaRepositorio.deletar(id);
    }
}