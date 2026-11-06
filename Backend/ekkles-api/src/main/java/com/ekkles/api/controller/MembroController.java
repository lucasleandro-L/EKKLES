package com.ekkles.api.controller;

import com.ekkles.api.dto.MembroRequest;
import com.ekkles.api.dto.MembroResponse;
import com.ekkles.api.model.StatusMembro;
import com.ekkles.api.service.MembroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/membros")
@RequiredArgsConstructor
public class MembroController {

    private final MembroService service;

    @PostMapping
    public ResponseEntity<MembroResponse> criar(@Valid @RequestBody MembroRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(req));
    }

    @GetMapping
    public List<MembroResponse> listar(
            @RequestParam(required = false) Long departamentoId,
            @RequestParam(required = false) StatusMembro status
    ) {
        return service.listar(departamentoId, status);
    }

    @GetMapping("/{id}")
    public MembroResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public MembroResponse atualizar(@PathVariable Long id, @Valid @RequestBody MembroRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
}