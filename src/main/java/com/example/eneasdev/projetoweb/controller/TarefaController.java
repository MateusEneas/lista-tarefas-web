package com.example.eneasdev.projetoweb.controller;

import com.example.eneasdev.projetoweb.model.Tarefa;
import com.example.eneasdev.projetoweb.model.Usuario;
import com.example.eneasdev.projetoweb.service.TarefaService;
import com.example.eneasdev.projetoweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarTarefas(Model model) {
        model.addAttribute("tarefas", tarefaService.findAll());
        model.addAttribute("usuarios", usuarioService.findAll());
        return "tarefas";
    }

    @GetMapping("/nova")
    public String novaTarefaForm(Tarefa tarefa, Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "nova_tarefa";
    }

    @PostMapping("/nova")
    public String novaTarefa(@ModelAttribute Tarefa tarefa, @RequestParam(required = false) Long usuarioId) {
        if (usuarioId != null) {
            Usuario usuario = usuarioService.findOrThrowNotFound(usuarioId);
            tarefa.setUsuario(usuario);
        }
        tarefaService.save(tarefa);
        return "redirect:/tarefas";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarTarefa(@PathVariable Long id, @RequestParam String status) {
        Tarefa tarefa = tarefaService.findOrThrowNotFound(id);
        tarefa.setStatus(status);
        tarefaService.save(tarefa);
        return "redirect:/tarefas";
    }

    @PostMapping("/deletar/{id}")
    public String deletarTarefa(@PathVariable Long id) {
        tarefaService.deleteById(id);
        return "redirect:/tarefas";
    }

    @GetMapping("/adicionar-usuario/{id}")
    public String adicionarUsuarioForm(@PathVariable Long id, Model model) {
        Tarefa tarefa = tarefaService.findOrThrowNotFound(id);
        model.addAttribute("tarefa", tarefa);
        model.addAttribute("usuarios", usuarioService.findAll());
        return "adicionar_usuario_tarefa";
    }

    @PostMapping("/adicionar-usuario/{id}")
    public String adicionarUsuario(@PathVariable Long id, @RequestParam Long usuarioId) {
        Tarefa tarefa = tarefaService.findOrThrowNotFound(id);
        Usuario usuario = usuarioService.findOrThrowNotFound(usuarioId);
        tarefaService.updateUsuario(id, usuario);
        return "redirect:/tarefas";
    }
}