package com.ekkles.api.controller;

import com.ekkles.api.dto.DepartamentoRequest;
import com.ekkles.api.dto.DepartamentoResponse;
import com.ekkles.api.service.DepartamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService service;

    @PostMapping
    public ResponseEntity<DepartamentoResponse> criar(@Valid @RequestBody DepartamentoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(req));
    }

    @GetMapping
    public List<DepartamentoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public DepartamentoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public DepartamentoResponse atualizar(@PathVariable Long id, @Valid @RequestBody DepartamentoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}