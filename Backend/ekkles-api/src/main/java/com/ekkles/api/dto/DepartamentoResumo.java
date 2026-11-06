package com.ekkles.api.dto;

import com.ekkles.api.model.Departamento;

public record DepartamentoResumo(Long id, String nome) {
    public static DepartamentoResumo from(Departamento d) {
        return new DepartamentoResumo(d.getId(), d.getNome());
    }
}