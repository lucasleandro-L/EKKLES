package com.ekkles.api.dto;

import com.ekkles.api.model.Membro;

public record MembroResumo(Long id, String nome) {
    public static MembroResumo from(Membro m) {
        return new MembroResumo(m.getId(), m.getNome());
    }
}