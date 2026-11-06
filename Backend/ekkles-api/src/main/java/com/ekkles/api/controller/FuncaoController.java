package com.ekkles.api.controller;

import com.ekkles.api.dto.FuncaoRequest;
import com.ekkles.api.dto.FuncaoResponse;
import com.ekkles.api.service.FuncaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/funcoes")
@RequiredArgsConstructor
public class FuncaoController {

    private final FuncaoService service;

    @PostMapping
    public ResponseEntity<FuncaoResponse> criar(@Valid @RequestBody FuncaoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(req));
    }

    @GetMapping
    public List<FuncaoResponse> listar(@RequestParam(required = false) Long departamentoId) {
        return service.listar(departamentoId);
    }

    @GetMapping("/{id}")
    public FuncaoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public FuncaoResponse atualizar(@PathVariable Long id, @Valid @RequestBody FuncaoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}