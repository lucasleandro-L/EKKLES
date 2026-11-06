package com.ekkles.api.repository;

import com.ekkles.api.model.Evento;
import com.ekkles.api.model.StatusEvento;
import com.ekkles.api.model.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByTipo(TipoEvento tipo);
    List<Evento> findByStatus(StatusEvento status);
    List<Evento> findByTipoAndStatus(TipoEvento tipo, StatusEvento status);
}