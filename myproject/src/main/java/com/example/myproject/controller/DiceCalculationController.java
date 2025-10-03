package com.example.myproject.controller;

import com.example.myproject.model.DiceCalculation;
import com.example.myproject.service.DiceCalculationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DiceCalculationController {

    private final DiceCalculationService service;

    public DiceCalculationController(DiceCalculationService service) {
        this.service = service;
    }

    // Pagina inicial
    @GetMapping("/")
    public String home() {
        return "redirect:/calculations";
    }

    // Listar
    @GetMapping("/calculations")
    public String listar(Model model) {
        model.addAttribute("calculations", service.listar());
        return "calculations/list";
    }

    // Formulario
    @GetMapping("/calculations/novo")
    public String novoForm(Model model) {
        model.addAttribute("calculation", new DiceCalculation());
        return "calculations/form";
    }

    // Criar nova entrada
    @PostMapping("/calculations")
    public String adicionar(@Valid @ModelAttribute DiceCalculation calculation,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "calculations/form";
        }
        try {
            service.adicionar(calculation);
            return "redirect:/calculations";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao salvar: " + e.getMessage());
            return "calculations/form";
        }
    }

    // Editar entrada (mesmo id)
    @GetMapping("/calculations/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        DiceCalculation calculation = service.buscar(id);
        if (calculation == null) {
            return "redirect:/calculations";
        }
        model.addAttribute("calculation", calculation);
        return "calculations/form";
    }

    // Ecluir entrada
    @GetMapping("/calculations/remover/{id}")
    public String remover(@PathVariable Long id) {
        service.remover(id);
        return "redirect:/calculations";
    }

    // Atualizar (para quando editar e salvar)
    @PostMapping("/calculations/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute DiceCalculation calculation,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "calculations/form";
        }

        DiceCalculation atualizado = service.atualizar(id, calculation);
        if (atualizado == null) {
            return "redirect:/calculations";
        }

        return "redirect:/calculations";
    }

    // ORDENAR por probabilidade
    @GetMapping("/calculations/ordenar/probabilidade")
    public String ordenarPorProbabilidade(Model model) {
        model.addAttribute("calculations", service.ordenarPorProbabilidade());
        return "calculations/list";
    }

    // BUSCAR por tipo
    @GetMapping("/calculations/buscar/tipo")
    public String buscarPorTipo(@RequestParam String tipo, Model model) {
        model.addAttribute("calculations", service.buscarPorTipo(tipo));
        return "calculations/list";
    }

    // BUSCAR por dados
    @GetMapping("/calculations/buscar/dados")
    public String buscarPorDados(@RequestParam Integer dados, Model model) {
        model.addAttribute("calculations", service.buscarPorDados(dados));
        return "calculations/list";
    }

    // BUSCAR por probabilidade maior que
    @GetMapping("/calculations/buscar/probabilidade")
    public String buscarProbabilidadeMaior(@RequestParam Double prob, Model model) {
        model.addAttribute("calculations", service.buscarProbabilidadeMaiorQue(prob));
        return "calculations/list";
    }

    // PESQUISAR por ID
    @GetMapping("/calculations/pesquisar")
    public String pesquisarPorId(@RequestParam Long id, Model model) {
        DiceCalculation calculation = service.buscar(id);
        if (calculation != null) {
            model.addAttribute("calculations", List.of(calculation));
        } else {
            model.addAttribute("calculations", List.of());
            model.addAttribute("error", "Cálculo com ID " + id + " não encontrado!");
        }
        return "calculations/list";
    }
}