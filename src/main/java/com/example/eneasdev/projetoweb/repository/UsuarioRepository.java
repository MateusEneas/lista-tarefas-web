package com.example.eneasdev.projetoweb.repository;

import com.example.eneasdev.projetoweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}