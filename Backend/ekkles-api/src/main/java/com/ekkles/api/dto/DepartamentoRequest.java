package com.ekkles.api.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartamentoRequest(
        @NotBlank String nome,
        String descricao,
        Long liderId
) {}