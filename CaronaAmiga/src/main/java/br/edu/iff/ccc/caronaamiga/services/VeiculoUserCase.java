package br.edu.iff.ccc.caronaamiga.services;

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.caronaamiga.dto.VeiculoRequest;
import br.edu.iff.ccc.caronaamiga.entities.Veiculo;
import br.edu.iff.ccc.caronaamiga.repositories.VeiculoRepositorio;


@Service
public class VeiculoUserCase {

    private final VeiculoRepositorio veiculoRepositorio;
    
    public VeiculoUserCase(VeiculoRepositorio veiculoRepositorio) {
        this.veiculoRepositorio = veiculoRepositorio;
    }

    public void criarVeiculo(br.edu.iff.ccc.caronaamiga.dto.VeiculoRequest veiculo) {
        // Lógica para criar um produto

        // Gerar um ID único para o produto, seguir critérios especificados (ex: UUID, sequência numérica, etc.)
        UUID id = UUID.randomUUID();
        Veiculo novoVeiculo = new Veiculo(id, veiculo.getModelo(), veiculo.getCor(), veiculo.getPlaca(), veiculo.getQuantidadeVagas());
        this.veiculoRepositorio.salvar(novoVeiculo);
    } 

    public void atualizarVeiculo() {
        // Lógica para atualizar um produto
    }

    public void deletarVeiculo() {
        // Lógica para deletar um produto
    }

    public void buscarVeiculo() {
        // Lógica para buscar um produto
    }

    public List<> listarVeiculo() {
        List<> veiculos = new List<>();
        return veiculos;
    }

    public void validarVeiculo() {
        // Lógica para validar um produto
    }   
}