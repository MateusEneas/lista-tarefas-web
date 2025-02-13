package com.example.eneasdev.projetoweb.service;

import com.example.eneasdev.projetoweb.exception.ResourceNotFoundException;
import com.example.eneasdev.projetoweb.model.Tarefa;
import com.example.eneasdev.projetoweb.model.Usuario;
import com.example.eneasdev.projetoweb.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    public Tarefa save(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deleteById(Long id) {
        tarefaRepository.deleteById(id);
    }

    public Optional<Tarefa> findById(Long id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa findOrThrowNotFound(Long id) {
        return tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
    }

    public void updateUsuario(Long tarefaId, Usuario usuario) {
        Tarefa tarefa = findOrThrowNotFound(tarefaId);
        tarefa.setUsuario(usuario);
        tarefaRepository.save(tarefa);
    }
}