package br.edu.iff.ccc.caronaamiga.repositories;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.caronaamiga.entities.Veiculo;

public class VeiculoRepositorio {

    private List<Veiculo> veiculos;

    public VeiculoRepositorio() {
        // Inicialização do repositório, se necessário
        this.veiculos = new ArrayList<Veiculo>(); 

    }

    public void salvar(Veiculo veiculo) {
        // Lógica para salvar o Veiculo no repositório
        this.veiculos.add(veiculo);
        System.out.println("Veículo salvo: " + veiculo.getPlaca());
    }

    public Veiculo buscarPorId(String id) {
        // Lógica para buscar um Veículo pelo ID no repositório
       return null; // Retornar o Veículo encontrado ou null se não encontrado
    }

}