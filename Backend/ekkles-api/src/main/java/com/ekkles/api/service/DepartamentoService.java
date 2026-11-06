package com.ekkles.api.service;

import com.ekkles.api.dto.DepartamentoRequest;
import com.ekkles.api.dto.DepartamentoResponse;
import com.ekkles.api.exception.ConflitoException;
import com.ekkles.api.exception.RecursoNaoEncontradoException;
import com.ekkles.api.exception.RegraDeNegocioException;
import com.ekkles.api.model.Departamento;
import com.ekkles.api.model.Membro;
import com.ekkles.api.repository.DepartamentoRepository;
import com.ekkles.api.repository.FuncaoRepository;
import com.ekkles.api.repository.MembroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository repository;
    private final MembroRepository membroRepository;
    private final FuncaoRepository funcaoRepository;

    public DepartamentoResponse criar(DepartamentoRequest req) {
        // RN-12: nome de departamento único
        if (repository.existsByNomeIgnoreCase(req.nome())) {
            throw new ConflitoException("Já existe um departamento com esse nome.");
        }
        Departamento departamento = new Departamento();
        preencher(departamento, req);
        return DepartamentoResponse.from(repository.save(departamento));
    }

    public List<DepartamentoResponse> listar() {
        return repository.findAll().stream().map(DepartamentoResponse::from).toList();
    }

    public DepartamentoResponse buscarPorId(Long id) {
        return DepartamentoResponse.from(buscarEntidade(id));
    }

    public DepartamentoResponse atualizar(Long id, DepartamentoRequest req) {
        Departamento departamento = buscarEntidade(id);
        repository.findByNomeIgnoreCase(req.nome()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new ConflitoException("Já existe um departamento com esse nome.");
            }
        });
        preencher(departamento, req);
        return DepartamentoResponse.from(repository.save(departamento));
    }

    public void excluir(Long id) {
        Departamento departamento = buscarEntidade(id);
        // RN-12: departamento com membros ou funções não pode ser excluído
        if (membroRepository.existsByDepartamentoId(id) || funcaoRepository.existsByDepartamentoId(id)) {
            throw new RegraDeNegocioException("Departamento possui membros ou funções e não pode ser excluído.");
        }
        repository.delete(departamento);
    }

    public Departamento buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Departamento não encontrado."));
    }

    private void preencher(Departamento departamento, DepartamentoRequest req) {
        departamento.setNome(req.nome());
        departamento.setDescricao(req.descricao());
        Membro lider = null;
        if (req.liderId() != null) {
            lider = membroRepository.findById(req.liderId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Membro (líder) não encontrado."));
        }
        departamento.setLider(lider);
    }
}