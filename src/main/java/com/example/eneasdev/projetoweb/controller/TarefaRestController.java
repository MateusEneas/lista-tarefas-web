package com.example.eneasdev.projetoweb.controller;

import com.example.eneasdev.projetoweb.model.Tarefa;
import com.example.eneasdev.projetoweb.model.Usuario;
import com.example.eneasdev.projetoweb.service.TarefaService;
import com.example.eneasdev.projetoweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/tarefas")
public class TarefaRestController {

    @Autowired
    private TarefaService tarefaService;
    @Autowired
    private UsuarioService usuarioService;

    // Lista todas as tarefas e usuários em formato JSON
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTarefas() {
        Map<String, Object> response = new HashMap<>();
        response.put("tarefas", tarefaService.findAll());
        return ResponseEntity.ok(response); // Resposta em JSON
    }

    // Cria uma nova tarefa
    @PostMapping("/nova")
    public ResponseEntity<Tarefa> novaTarefa(@RequestBody Tarefa tarefa, @RequestParam(required = false) Long usuarioId) {
        if (usuarioId != null) {
            Usuario usuario = usuarioService.findOrThrowNotFound(usuarioId);
            tarefa.setUsuario(usuario); // Associando o usuário
        }
        tarefaService.save(tarefa);  // Salvando a tarefa
        return ResponseEntity.ok(tarefa);  // Retorna a tarefa criada com status 200
    }


    // Atualiza o status de uma tarefa
    @PostMapping("/atualizar/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestParam String status) {
        Tarefa tarefa = tarefaService.findOrThrowNotFound(id);
        tarefa.setStatus(status);
        tarefaService.save(tarefa);
        return ResponseEntity.ok(tarefa); // Retorna a tarefa com o status atualizado em JSON
    }

    // Deleta uma tarefa
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Map<String, String>> deletarTarefa(@PathVariable Long id) {
        tarefaService.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tarefa deletada com sucesso.");
        return ResponseEntity.ok(response); // Retorna mensagem de sucesso em JSON
    }

    // Adiciona um usuário a uma tarefa
    @PostMapping("/adicionar-usuario/{id}")
    public ResponseEntity<Tarefa> adicionarUsuario(@PathVariable Long id, @RequestParam Long usuarioId) {
        Tarefa tarefa = tarefaService.findOrThrowNotFound(id);
        Usuario usuario = usuarioService.findOrThrowNotFound(usuarioId);
        tarefaService.updateUsuario(id, usuario);
        return ResponseEntity.ok(tarefa); // Retorna a tarefa atualizada em JSON
    }
}
