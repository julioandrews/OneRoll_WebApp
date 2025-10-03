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
@RequestMapping("/calculations")
public class DiceCalculationController {

    private final DiceCalculationService service;

    public DiceCalculationController(DiceCalculationService service) {
        this.service = service;
    }

    // LISTAR todos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("calculations", service.listar());
        return "calculations/list";
    }

    // FORMULÁRIO novo
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("calculation", new DiceCalculation());
        return "calculations/form";
    }

    // CRIAR novo
    @PostMapping
    public String adicionar(@Valid @ModelAttribute DiceCalculation calculation,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "calculations/form";
        }
        service.adicionar(calculation);
        model.addAttribute("success", "Cálculo salvo com sucesso!");
        return "redirect:/calculations";
    }

    // EDITAR formulário
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        DiceCalculation calculation = service.buscar(id);
        if (calculation == null) {
            model.addAttribute("error", "Cálculo não encontrado!");
            return "redirect:/calculations";
        }
        model.addAttribute("calculation", calculation);
        return "calculations/form";
    }

    // ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute DiceCalculation calculation,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "calculations/form";
        }

        DiceCalculation atualizado = service.atualizar(id, calculation);
        if (atualizado == null) {
            model.addAttribute("error", "Cálculo não encontrado para atualização!");
            return "redirect:/calculations";
        }

        model.addAttribute("success", "Cálculo atualizado com sucesso!");
        return "redirect:/calculations";
    }

    // EXCLUIR
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        service.remover(id);
        model.addAttribute("success", "Cálculo excluído com sucesso!");
        return "redirect:/calculations";
    }

    // PESQUISAR por ID (requisito mínimo)
    @GetMapping("/pesquisar")
    public String pesquisarPorId(@RequestParam Long id, Model model) {
        DiceCalculation calculation = service.buscar(id);
        if (calculation == null) {
            model.addAttribute("error", "Cálculo com ID " + id + " não encontrado!");
            model.addAttribute("calculations", service.listar());
        } else {
            model.addAttribute("calculations", List.of(calculation));
        }
        return "calculations/list";
    }

    // NOVOS MÉTODOS DE BUSCA (extra)
    @GetMapping("/buscar/tipo")
    public String buscarPorTipo(@RequestParam String tipo, Model model) {
        model.addAttribute("calculations", service.buscarPorTipo(tipo));
        return "calculations/list";
    }

    @GetMapping("/buscar/dados")
    public String buscarPorDados(@RequestParam Integer dados, Model model) {
        model.addAttribute("calculations", service.buscarPorDados(dados));
        return "calculations/list";
    }

    @GetMapping("/buscar/probabilidade")
    public String buscarProbabilidadeMaior(@RequestParam Double prob, Model model) {
        model.addAttribute("calculations", service.buscarProbabilidadeMaiorQue(prob));
        return "calculations/list";
    }

    @GetMapping("/ordenar/probabilidade")
    public String ordenarPorProbabilidade(Model model) {
        model.addAttribute("calculations", service.ordenarPorProbabilidade());
        return "calculations/list";
    }
    @GetMapping("/")
    public String home() {
        return "index"; // ou "redirect:/calculations" se quiser ir direto para a lista
    }
}