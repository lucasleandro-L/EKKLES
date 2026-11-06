package com.ekkles.api.dto;

import com.ekkles.api.model.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoRequest(
        @NotBlank String titulo,
        String descricao,
        @NotNull LocalDate dataEvento,
        @NotNull LocalTime horario,
        String local,
        @NotNull TipoEvento tipo,
        @Positive Integer capacidade
) {}