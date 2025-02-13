package com.example.eneasdev.projetoweb.service;

import com.example.eneasdev.projetoweb.exception.BadRequestException;
import com.example.eneasdev.projetoweb.exception.ResourceNotFoundException;
import com.example.eneasdev.projetoweb.model.Usuario;
import com.example.eneasdev.projetoweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario findOrThrowNotFound(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public void deleteWithValidation(Long id) {
        Usuario usuario = findOrThrowNotFound(id);
        if (usuario.getTarefas() != null && !usuario.getTarefas().isEmpty()) {
            throw new BadRequestException("Não é possível excluir o usuário porque ele possui tarefas associadas.");
        }
        deleteById(id);
    }
}