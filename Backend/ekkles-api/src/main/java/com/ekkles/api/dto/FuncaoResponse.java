package com.ekkles.api.dto;

import com.ekkles.api.model.Funcao;

public record FuncaoResponse(Long id, String nome, String descricao, DepartamentoResumo departamento) {
    public static FuncaoResponse from(Funcao f) {
        return new FuncaoResponse(f.getId(), f.getNome(), f.getDescricao(), DepartamentoResumo.from(f.getDepartamento()));
    }
}