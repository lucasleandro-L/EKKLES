package com.ekkles.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncaoRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull Long departamentoId
) {}