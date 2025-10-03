package com.example.myproject.repository;

import com.example.myproject.model.DiceCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface DiceCalculationRepository extends JpaRepository<DiceCalculation, Long> {

    // Buscar por tipo de cálculo
    List<DiceCalculation> findByCalculationType(String calculationType);

    // Buscar por número de dados
    List<DiceCalculation> findByNumDice(Integer numDice);

    // Buscar cálculos com probabilidade maior que X
    List<DiceCalculation> findByProbabilityGreaterThan(Double probability);

    // Buscar cálculos com probabilidade entre X e Y
    List<DiceCalculation> findByProbabilityBetween(Double minProb, Double maxProb);

    // Buscar por número de lados
    List<DiceCalculation> findByNumSides(Integer numSides);

    // Buscar por largura mínima
    List<DiceCalculation> findByMinWidth(Integer minWidth);

    // Buscar por altura mínima
    List<DiceCalculation> findByMinHeight(Integer minHeight);

    // Buscar ordenado por probabilidade (decrescente)
    List<DiceCalculation> findAllByOrderByProbabilityDesc();

    // Buscar ordenado por data de criação (decrescente)
    List<DiceCalculation> findAllByOrderByCreatedAtDesc();

    // Query personalizada - buscar cálculos complexos
    @Query("SELECT d FROM DiceCalculation d WHERE d.numDice >= :minDice AND d.probability > :minProb ORDER BY d.probability DESC")
    List<DiceCalculation> buscarCalculosComplexos(@Param("minDice") Integer minDice, @Param("minProb") Double minProb);

    // Query personalizada - buscar por tipo e número de dados
    @Query("SELECT d FROM DiceCalculation d WHERE d.calculationType = :tipo AND d.numDice = :dados")
    List<DiceCalculation> buscarPorTipoEDados(@Param("tipo") String tipo, @Param("dados") Integer dados);
}