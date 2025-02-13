package com.example.eneasdev.projetoweb.repository;

import com.example.eneasdev.projetoweb.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}