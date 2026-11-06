package com.ekkles.api.controller;

import com.ekkles.api.dto.EventoRequest;
import com.ekkles.api.dto.EventoResponse;
import com.ekkles.api.model.StatusEvento;
import com.ekkles.api.model.TipoEvento;
import com.ekkles.api.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;

    @PostMapping
    public ResponseEntity<EventoResponse> criar(@Valid @RequestBody EventoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(req));
    }

    @GetMapping
    public List<EventoResponse> listar(
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) StatusEvento status
    ) {
        return service.listar(tipo, status);
    }

    @GetMapping("/{id}")
    public EventoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EventoResponse atualizar(@PathVariable Long id, @Valid @RequestBody EventoRequest req) {
        return service.atualizar(id, req);
    }

    @PatchMapping("/{id}/cancelar")
    public EventoResponse cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}