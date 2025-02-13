package com.example.eneasdev.projetoweb.controller;

import com.example.eneasdev.projetoweb.exception.BadRequestException;
import com.example.eneasdev.projetoweb.exception.ResourceNotFoundException;
import com.example.eneasdev.projetoweb.model.Usuario;
import com.example.eneasdev.projetoweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    // Lista todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios); // Retorna lista de usuários em formato JSON
    }

    // Cria um novo usuário
    @PostMapping("/novo")
    public ResponseEntity<Usuario> novoUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioSalvo); // Retorna o usuário criado com status 200
    }

    // Deleta um usuário
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Map<String, String>> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteWithValidation(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuário deletado com sucesso.");
            return ResponseEntity.ok(response); // Retorna mensagem de sucesso
        } catch (BadRequestException | ResourceNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response); // Retorna mensagem de erro com status 400
        }
    }

    // Exibe o formulário de edição de usuário
    @GetMapping("/editar/{id}")
    public ResponseEntity<Usuario> editarUsuarioForm(@PathVariable Long id) {
        Usuario usuario = usuarioService.findOrThrowNotFound(id);
        return ResponseEntity.ok(usuario); // Retorna o usuário para edição
    }

    // Atualiza um usuário
    @PostMapping("/editar/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioService.findOrThrowNotFound(id);
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioService.save(usuarioExistente);
        return ResponseEntity.ok(usuarioExistente); // Retorna o usuário atualizado
    }
}
