package com.ekkles.api.dto;

import com.ekkles.api.model.Evento;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoResponse(
        Long id,
        String titulo,
        String descricao,
        LocalDate dataEvento,
        LocalTime horario,
        String local,
        String tipo,
        Integer capacidade,
        String status
) {
    public static EventoResponse from(Evento e) {
        return new EventoResponse(
                e.getId(),
                e.getTitulo(),
                e.getDescricao(),
                e.getDataEvento(),
                e.getHorario(),
                e.getLocal(),
                e.getTipo().name(),
                e.getCapacidade(),
                e.getStatus().name()
        );
    }
}