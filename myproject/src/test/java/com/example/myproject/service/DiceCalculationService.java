package com.example.myproject.service;

import com.example.myproject.model.DiceCalculation;
import com.example.myproject.repository.DiceCalculationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiceCalculationService {

    private final DiceCalculationRepository repository;

    public DiceCalculationService(DiceCalculationRepository repository) {
        this.repository = repository;
    }

    // CRUD Básico
    public List<DiceCalculation> listar() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public DiceCalculation buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public DiceCalculation adicionar(DiceCalculation calculo) {
        calculo.setProbability(calcularProbabilidade(calculo));
        return repository.save(calculo);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    public DiceCalculation atualizar(Long id, DiceCalculation calculoAtualizado) {
        return repository.findById(id)
                .map(calculo -> {
                    calculo.setNumDice(calculoAtualizado.getNumDice());
                    calculo.setNumSides(calculoAtualizado.getNumSides());
                    calculo.setCalculationType(calculoAtualizado.getCalculationType());
                    calculo.setMinWidth(calculoAtualizado.getMinWidth());
                    calculo.setMinHeight(calculoAtualizado.getMinHeight());
                    calculo.setSpecificAmount(calculoAtualizado.getSpecificAmount());
                    calculo.setSpecificWidth(calculoAtualizado.getSpecificWidth());

                    calculo.setProbability(calcularProbabilidade(calculoAtualizado));
                    return repository.save(calculo);
                })
                .orElse(null);
    }

    // MÉTODOS DE BUSCA (usando o repository completo)
    public List<DiceCalculation> buscarPorTipo(String tipo) {
        return repository.findByCalculationType(tipo);
    }

    public List<DiceCalculation> buscarPorDados(Integer numDados) {
        return repository.findByNumDice(numDados);
    }

    public List<DiceCalculation> buscarPorLados(Integer numLados) {
        return repository.findByNumSides(numLados);
    }

    public List<DiceCalculation> buscarProbabilidadeMaiorQue(Double probabilidade) {
        return repository.findByProbabilityGreaterThan(probabilidade);
    }

    public List<DiceCalculation> buscarProbabilidadeEntre(Double min, Double max) {
        return repository.findByProbabilityBetween(min, max);
    }

    public List<DiceCalculation> buscarPorLargura(Integer largura) {
        return repository.findByMinWidth(largura);
    }

    public List<DiceCalculation> buscarPorAltura(Integer altura) {
        return repository.findByMinHeight(altura);
    }

    public List<DiceCalculation> ordenarPorProbabilidade() {
        return repository.findAllByOrderByProbabilityDesc();
    }

    public List<DiceCalculation> buscarCalculosComplexos(Integer minDados, Double minProb) {
        return repository.buscarCalculosComplexos(minDados, minProb);
    }

    public List<DiceCalculation> buscarPorTipoEDados(String tipo, Integer dados) {
        return repository.buscarPorTipoEDados(tipo, dados);
    }

    // Cálculo de probabilidade (simplificado para demonstração)
    private Double calcularProbabilidade(DiceCalculation calc) {
        // Aqui você pode inserir suas funções complexas originais
        double base = 1.0 / (calc.getNumSides() * calc.getNumDice());

        switch(calc.getCalculationType()) {
            case "width":
                return calc.getMinWidth() != null ? base * calc.getMinWidth() : base;
            case "height":
                return calc.getMinHeight() != null ? base * calc.getMinHeight() : base;
            case "both":
                return (calc.getMinWidth() != null && calc.getMinHeight() != null) ?
                        base * calc.getMinWidth() * calc.getMinHeight() : base;
            case "specific":
                return (calc.getSpecificAmount() != null && calc.getSpecificWidth() != null) ?
                        base * calc.getSpecificAmount() * calc.getSpecificWidth() : base;
            default:
                return base;
        }
    }
}