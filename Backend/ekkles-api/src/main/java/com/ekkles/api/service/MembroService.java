package com.ekkles.api.service;

import com.ekkles.api.dto.MembroRequest;
import com.ekkles.api.dto.MembroResponse;
import com.ekkles.api.exception.ConflitoException;
import com.ekkles.api.exception.RecursoNaoEncontradoException;
import com.ekkles.api.model.Departamento;
import com.ekkles.api.model.Membro;
import com.ekkles.api.model.StatusMembro;
import com.ekkles.api.model.Usuario;
import com.ekkles.api.repository.DepartamentoRepository;
import com.ekkles.api.repository.MembroRepository;
import com.ekkles.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembroService {

    private final MembroRepository repository;
    private final DepartamentoRepository departamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public MembroResponse criar(MembroRequest req) {
        Membro membro = new Membro();
        preencher(membro, req);

        membro.setReceberNotificacoes(req.receberNotificacoes() == null || req.receberNotificacoes());
        return MembroResponse.from(repository.save(membro));
    }

    public List<MembroResponse> listar(Long departamentoId, StatusMembro status) {
        List<Membro> membros;
        if (departamentoId != null && status != null) {
            membros = repository.findByDepartamentoIdAndStatus(departamentoId, status);
        } else if (departamentoId != null) {
            membros = repository.findByDepartamentoId(departamentoId);
        } else if (status != null) {
            membros = repository.findByStatus(status);
        } else {
            membros = repository.findAll();
        }
        return membros.stream().map(MembroResponse::from).toList();
    }

    public MembroResponse buscarPorId(Long id) {
        return MembroResponse.from(buscarEntidade(id));
    }

    public MembroResponse atualizar(Long id, MembroRequest req) {
        Membro membro = buscarEntidade(id);
        preencher(membro, req);
        if (req.receberNotificacoes() != null) {
            membro.setReceberNotificacoes(req.receberNotificacoes());
        }
        return MembroResponse.from(repository.save(membro));
    }

    public void inativar(Long id) {
        Membro membro = buscarEntidade(id);
        membro.setStatus(StatusMembro.INATIVO);
        repository.save(membro);
    }

    public Membro buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Membro não encontrado."));
    }

    private void preencher(Membro membro, MembroRequest req) {
        membro.setNome(req.nome());
        membro.setDataNascimento(req.dataNascimento());
        membro.setTelefone(req.telefone());
        membro.setEmail(req.email());

        Departamento departamento = null;
        if (req.departamentoId() != null) {
            departamento = departamentoRepository.findById(req.departamentoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Departamento não encontrado."));
        }
        membro.setDepartamento(departamento);

        Usuario usuario = null;
        if (req.usuarioId() != null) {
            usuario = usuarioRepository.findById(req.usuarioId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
            repository.findByUsuarioId(req.usuarioId()).ifPresent(outro -> {
                if (membro.getId() == null || !outro.getId().equals(membro.getId())) {
                    throw new ConflitoException("Este usuário já está vinculado a outro membro.");
                }
            });
        }
        membro.setUsuario(usuario);
    }
}