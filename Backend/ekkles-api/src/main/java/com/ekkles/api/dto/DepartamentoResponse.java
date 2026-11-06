package com.ekkles.api.dto;

import com.ekkles.api.model.Departamento;

public record DepartamentoResponse(Long id, String nome, String descricao, MembroResumo lider) {
    public static DepartamentoResponse from(Departamento d) {
        return new DepartamentoResponse(
                d.getId(),
                d.getNome(),
                d.getDescricao(),
                d.getLider() == null ? null : MembroResumo.from(d.getLider())
        );
    }
}