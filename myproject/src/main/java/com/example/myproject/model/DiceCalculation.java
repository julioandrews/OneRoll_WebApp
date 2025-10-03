package com.example.myproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dice_calculations")
public class DiceCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Número de dados é obrigatório!")
    @Min(value = 1, message = "Mínimo 1 dado!")
    @Max(value = 10, message = "Máximo 10 dados!")
    private Integer numDice;

    @NotNull(message = "Número de lados é obrigatório!")
    @Min(value = 1, message = "Mínimo 1 lado!")
    @Max(value = 10, message = "Máximo 10 lados!")
    private Integer numSides;

    @NotBlank(message = "Tipo de cálculo é obrigatório!")
    private String calculationType;

    // Campos condicionais
    private Integer minWidth;
    private Integer minHeight;

    private Double probability;
    private LocalDateTime createdAt;

    // Construtor
    public DiceCalculation() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumDice() { return numDice; }
    public void setNumDice(Integer numDice) { this.numDice = numDice; }

    public Integer getNumSides() { return numSides; }
    public void setNumSides(Integer numSides) { this.numSides = numSides; }

    public String getCalculationType() { return calculationType; }
    public void setCalculationType(String calculationType) { this.calculationType = calculationType; }

    public Integer getMinWidth() { return minWidth; }
    public void setMinWidth(Integer minWidth) { this.minWidth = minWidth; }

    public Integer getMinHeight() { return minHeight; }
    public void setMinHeight(Integer minHeight) { this.minHeight = minHeight; }

    public Double getProbability() { return probability; }
    public void setProbability(Double probability) { this.probability = probability; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}