package com.example.myproject.repository;

import com.example.myproject.model.DiceCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface DiceCalculationRepository extends JpaRepository<DiceCalculation, Long> {

    List<DiceCalculation> findByCalculationType(String calculationType);
    List<DiceCalculation> findAllByOrderByProbabilityDesc();
    List<DiceCalculation> findAllByOrderByCreatedAtDesc();
    List<DiceCalculation> findAllByOrderByIdDesc();
}