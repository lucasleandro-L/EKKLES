package com.ekkles.api.service;

import com.ekkles.api.dto.UsuarioRequest;
import com.ekkles.api.dto.UsuarioResponse;
import com.ekkles.api.exception.ConflitoException;
import com.ekkles.api.exception.RecursoNaoEncontradoException;
import com.ekkles.api.model.Perfil;
import com.ekkles.api.model.Usuario;
import com.ekkles.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioResponse criar(UsuarioRequest req) {

        if (repository.existsByEmailIgnoreCase(req.email())) {
            throw new ConflitoException("Já existe um usuário cadastrado com esse e-mail.");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(req.nome());
        usuario.setEmail(req.email());
        usuario.setSenha(req.senha());
        usuario.setPerfil(req.perfil() != null ? req.perfil() : Perfil.MEMBRO);
        return UsuarioResponse.from(repository.save(usuario));
    }

    public List<UsuarioResponse> listar() {
        return repository.findAll().stream().map(UsuarioResponse::from).toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        return UsuarioResponse.from(buscarEntidade(id));
    }

    public UsuarioResponse atualizar(Long id, UsuarioRequest req) {
        Usuario usuario = buscarEntidade(id);
        repository.findByEmailIgnoreCase(req.email()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new ConflitoException("Já existe um usuário cadastrado com esse e-mail.");
            }
        });
        usuario.setNome(req.nome());
        usuario.setEmail(req.email());
        usuario.setSenha(req.senha());
        if (req.perfil() != null) {
            usuario.setPerfil(req.perfil());
        }
        return UsuarioResponse.from(repository.save(usuario));
    }

    public void inativar(Long id) {
        Usuario usuario = buscarEntidade(id);
        usuario.setAtivo(false);
        repository.save(usuario);
    }

    public Usuario buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
    }
}