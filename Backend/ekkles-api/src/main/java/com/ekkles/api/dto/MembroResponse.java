package com.ekkles.api.dto;

import com.ekkles.api.model.Membro;

import java.time.LocalDate;

public record MembroResponse(
        Long id,
        String nome,
        LocalDate dataNascimento,
        String telefone,
        String email,
        String status,
        boolean receberNotificacoes,
        DepartamentoResumo departamento
) {
    public static MembroResponse from(Membro m) {
        return new MembroResponse(
                m.getId(),
                m.getNome(),
                m.getDataNascimento(),
                m.getTelefone(),
                m.getEmail(),
                m.getStatus().name(),
                m.isReceberNotificacoes(),
                m.getDepartamento() == null ? null : DepartamentoResumo.from(m.getDepartamento())
        );
    }
}