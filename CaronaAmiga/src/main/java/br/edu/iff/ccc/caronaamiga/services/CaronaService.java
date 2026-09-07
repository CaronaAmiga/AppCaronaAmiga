package br.edu.iff.ccc.caronaamiga.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.CaronaDTO;
import br.edu.iff.ccc.caronaamiga.entities.Carona;
import br.edu.iff.ccc.caronaamiga.entities.HistoricoViagem;
import br.edu.iff.ccc.caronaamiga.entities.StatusCarona;
import br.edu.iff.ccc.caronaamiga.entities.TipoPerfil;
import br.edu.iff.ccc.caronaamiga.entities.Usuario;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;
import br.edu.iff.ccc.caronaamiga.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc.caronaamiga.exception.RegraDeNegocioException;
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
        Usuario motorista = this.usuarioRepositorio.findById(dto.getMotoristaId()).orElseThrow(() -> new RecursoNaoEncontradoException("Motorista não encontrado com o id: " + dto.getMotoristaId()));

        if (motorista.getPerfilAtivo() != TipoPerfil.MOTORISTA) {
            throw new RegraDeNegocioException("Apenas usuários com perfil ativo de Motorista podem oferecer caronas.");
        }

        Veiculo veiculo = this.veiculoRepositorio.findById(dto.getVeiculoId()).orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado com o id: " + dto.getVeiculoId()));

        if (veiculo.getMotorista() == null || !veiculo.getMotorista().getId().equals(motorista.getId())) {
            throw new RegraDeNegocioException("O veículo selecionado não pertence ao motorista condutor.");
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
        if(dto.getVagasDisponiveis() > 0){
            vagas = dto.getVagasDisponiveis();
        } 
        
        else if(veiculo.getQuantidadeVagas() > 0){
            vagas = veiculo.getQuantidadeVagas();
        } 
        
        else{
            vagas = 4; 
        }

        carona.setVagasDisponiveis(vagas);
        
        if(dto.getValorRateio() > 0) {
            carona.setValorRateio(dto.getValorRateio());
        } 
        
        else {
            carona.calcularRateio();
        }
        this.caronaRepositorio.save(carona);
        return carona;
    }

    public List<Carona> buscarTodasAtivas(){
        return this.caronaRepositorio.findAll().stream()
            .filter(c -> c.getStatus() == StatusCarona.AGENDADA || c.getStatus() == StatusCarona.EM_ANDAMENTO)
            .toList();
    }

    public List<Carona> buscarCaronas(String periodo, String destinoCampus, Double maxPreco){
        return this.caronaRepositorio.findAll().stream()
            .filter(c -> c.getStatus() == StatusCarona.AGENDADA)
            .filter(c -> (destinoCampus == null || destinoCampus.isBlank() || (c.getDestino() != null && c.getDestino().toLowerCase().contains(destinoCampus.toLowerCase()))))
            .filter(c -> (maxPreco == null || c.getValorRateio() <= maxPreco))
            .toList();
    }

    public List<Carona> listarTodas() {
        return this.caronaRepositorio.findAll();
    }

    public List<Carona> buscarPorDestino(String destino) {
        if (destino == null || destino.isBlank()) {
            return listarTodas();
        }
        return this.caronaRepositorio.findByDestinoContainingIgnoreCase(destino);
    }

    public Carona buscarPorId(Long id) {
        return this.caronaRepositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Carona não encontrada com o id: " + id));
    }  

    public List<Carona> listarPorMotorista(Long motoristaId){
        return this.caronaRepositorio.findByMotoristaId(motoristaId);
    }

    public void iniciarCarona(Long id){
        Carona carona = buscarPorId(id);
        if(carona.getStatus() != StatusCarona.AGENDADA){
            throw new RegraDeNegocioException("Apenas caronas com status AGENDADA podem ser iniciadas.");
        }
        carona.iniciarCarona();
        this.caronaRepositorio.save(carona);
    }

    public void concluirCarona(Long id){
        Carona carona = buscarPorId(id);
        if(carona.getStatus() != StatusCarona.EM_ANDAMENTO){
            throw new RegraDeNegocioException("Apenas caronas com status EM_ANDAMENTO podem ser concluídas.");
        }
        carona.concluirCarona();
        this.caronaRepositorio.save(carona);
        if(carona.getMotorista() != null){
            HistoricoViagem historico = new HistoricoViagem(null, LocalDateTime.now(), carona.getMotorista(), carona);
            this.historicoViagemRepositorio.save(historico);
        }
    }

    public void cancelarCarona(Long id){
        Carona carona = buscarPorId(id);
        if(carona.getStatus() == StatusCarona.CONCLUIDA){
            throw new RegraDeNegocioException("Não é possível cancelar uma carona que já foi concluída.");
        }
        if(carona.getStatus() == StatusCarona.CANCELADA){
            throw new RegraDeNegocioException("A carona já se encontra cancelada.");
        }
        carona.cancelarCarona();
        this.caronaRepositorio.save(carona);
    }

    public void deletarCarona(Long id){
        Carona carona = buscarPorId(id);
        this.caronaRepositorio.delete(carona);
    }
}