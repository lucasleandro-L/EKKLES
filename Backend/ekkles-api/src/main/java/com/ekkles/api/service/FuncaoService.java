package com.ekkles.api.service;

import com.ekkles.api.dto.FuncaoRequest;
import com.ekkles.api.dto.FuncaoResponse;
import com.ekkles.api.exception.RecursoNaoEncontradoException;
import com.ekkles.api.model.Departamento;
import com.ekkles.api.model.Funcao;
import com.ekkles.api.repository.FuncaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncaoService {

    private final FuncaoRepository repository;
    private final DepartamentoService departamentoService;

    public FuncaoResponse criar(FuncaoRequest req) {
        Departamento departamento = departamentoService.buscarEntidade(req.departamentoId());
        Funcao funcao = new Funcao();
        funcao.setNome(req.nome());
        funcao.setDescricao(req.descricao());
        funcao.setDepartamento(departamento);
        return FuncaoResponse.from(repository.save(funcao));
    }

    public List<FuncaoResponse> listar(Long departamentoId) {
        List<Funcao> funcoes = departamentoId != null
                ? repository.findByDepartamentoId(departamentoId)
                : repository.findAll();
        return funcoes.stream().map(FuncaoResponse::from).toList();
    }

    public FuncaoResponse buscarPorId(Long id) {
        return FuncaoResponse.from(buscarEntidade(id));
    }

    public FuncaoResponse atualizar(Long id, FuncaoRequest req) {
        Funcao funcao = buscarEntidade(id);
        Departamento departamento = departamentoService.buscarEntidade(req.departamentoId());
        funcao.setNome(req.nome());
        funcao.setDescricao(req.descricao());
        funcao.setDepartamento(departamento);
        return FuncaoResponse.from(repository.save(funcao));
    }

    public void excluir(Long id) {
        // RN-07: quando a Escala existir, bloquear a exclusão de função que já foi escalada
        Funcao funcao = buscarEntidade(id);
        repository.delete(funcao);
    }

    public Funcao buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Função não encontrada."));
    }
}