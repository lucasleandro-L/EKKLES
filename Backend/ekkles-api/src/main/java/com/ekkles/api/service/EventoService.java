package com.ekkles.api.service;

import com.ekkles.api.dto.EventoRequest;
import com.ekkles.api.dto.EventoResponse;
import com.ekkles.api.exception.RecursoNaoEncontradoException;
import com.ekkles.api.exception.RegraDeNegocioException;
import com.ekkles.api.model.Evento;
import com.ekkles.api.model.StatusEvento;
import com.ekkles.api.model.TipoEvento;
import com.ekkles.api.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;

    public EventoResponse criar(EventoRequest req) {
        validarData(req.dataEvento());
        Evento evento = new Evento();
        preencher(evento, req);
        evento.setStatus(StatusEvento.AGENDADO);
        return EventoResponse.from(repository.save(evento));
    }

    public List<EventoResponse> listar(TipoEvento tipo, StatusEvento status) {
        List<Evento> eventos;
        if (tipo != null && status != null) {
            eventos = repository.findByTipoAndStatus(tipo, status);
        } else if (tipo != null) {
            eventos = repository.findByTipo(tipo);
        } else if (status != null) {
            eventos = repository.findByStatus(status);
        } else {
            eventos = repository.findAll();
        }
        return eventos.stream().map(EventoResponse::from).toList();
    }

    public EventoResponse buscarPorId(Long id) {
        return EventoResponse.from(buscarEntidade(id));
    }

    public EventoResponse atualizar(Long id, EventoRequest req) {
        validarData(req.dataEvento());
        Evento evento = buscarEntidade(id);
        preencher(evento, req);
        return EventoResponse.from(repository.save(evento));
    }

    public EventoResponse cancelar(Long id) {
        Evento evento = buscarEntidade(id);
        evento.setStatus(StatusEvento.CANCELADO);
        return EventoResponse.from(repository.save(evento));
    }

    public void excluir(Long id) {
        // RN-11: quando a Escala existir, bloquear a exclusão de evento que tem escalas
        Evento evento = buscarEntidade(id);
        repository.delete(evento);
    }

    public Evento buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado."));
    }

    private void preencher(Evento evento, EventoRequest req) {
        evento.setTitulo(req.titulo());
        evento.setDescricao(req.descricao());
        evento.setDataEvento(req.dataEvento());
        evento.setHorario(req.horario());
        evento.setLocal(req.local());
        evento.setTipo(req.tipo());
        evento.setCapacidade(req.capacidade());
    }

    private void validarData(LocalDate dataEvento) {
        // RN-02: não é permitido cadastrar ou atualizar evento com data anterior à atual
        if (dataEvento.isBefore(LocalDate.now())) {
            throw new RegraDeNegocioException("A data do evento não pode ser anterior à data atual.");
        }
    }
}