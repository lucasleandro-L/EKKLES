package com.ekkles.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record MembroRequest(
        @NotBlank String nome,
        @PastOrPresent LocalDate dataNascimento,
        String telefone,
        @Email String email,
        Long departamentoId,
        Long usuarioId,
        Boolean receberNotificacoes
) {}