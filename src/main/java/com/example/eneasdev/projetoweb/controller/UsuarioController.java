package com.example.eneasdev.projetoweb.controller;

import com.example.eneasdev.projetoweb.exception.BadRequestException;
import com.example.eneasdev.projetoweb.exception.ResourceNotFoundException;
import com.example.eneasdev.projetoweb.model.Usuario;
import com.example.eneasdev.projetoweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuarios";
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Usuario usuario) {
        return "novo_usuario";
    }

    @PostMapping("/novo")
    public String novoUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.save(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteWithValidation(id);
        } catch (BadRequestException | ResourceNotFoundException e) {
            return "redirect:/usuarios?error=" + e.getMessage();
        }
        return "redirect:/usuarios";
    }

    // Método para exibir o formulário de edição de usuário
    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findOrThrowNotFound(id);
        model.addAttribute("usuario", usuario);
        return "editar_usuario";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioService.findOrThrowNotFound(id);
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioService.save(usuarioExistente);
        return "redirect:/usuarios";
    }
}