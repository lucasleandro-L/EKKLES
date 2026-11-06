package com.ekkles.api.repository;

import com.ekkles.api.model.Funcao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncaoRepository extends JpaRepository<Funcao, Long> {
    List<Funcao> findByDepartamentoId(Long departamentoId);
    boolean existsByDepartamentoId(Long departamentoId);
}