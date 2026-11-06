package com.ekkles.api.repository;

import com.ekkles.api.model.Membro;
import com.ekkles.api.model.StatusMembro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembroRepository extends JpaRepository<Membro, Long> {
    List<Membro> findByDepartamentoId(Long departamentoId);
    List<Membro> findByStatus(StatusMembro status);
    List<Membro> findByDepartamentoIdAndStatus(Long departamentoId, StatusMembro status);
    boolean existsByDepartamentoId(Long departamentoId);
    Optional<Membro> findByUsuarioId(Long usuarioId);
}