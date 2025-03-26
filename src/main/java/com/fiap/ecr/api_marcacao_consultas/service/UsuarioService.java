package com.fiap.ecr.api_marcacao_consultas.service;

import com.fiap.ecr.api_marcacao_consultas.model.Usuario;
import com.fiap.ecr.api_marcacao_consultas.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> listarMedicos() {
        return usuarioRepository.findByTipo("MEDICO");
    }

    public List<Usuario> buscarMedicosPorEspecialidade(String especialidade) {
        // Esta implementação depende de como você relaciona médicos com especialidades
        // Se você tiver um relacionamento direto, pode usar:
        // return usuarioRepository.findByTipoAndEspecialidade("MEDICO", especialidade);

        // Caso contrário, você precisará implementar uma lógica personalizada
        // Esta é uma implementação simplificada:
        // Manteremos assim e implementaremos no futuro, se precisar!
        return usuarioRepository.findByTipo("MEDICO");
    }

    public Usuario salvarUsuario(Usuario usuario) {
        // Verifica se o e-mail já está cadastrado
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("Erro: Este e-mail já está cadastrado.");
        }

        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o e-mail já está sendo usado por outro usuário
        if (!usuarioExistente.getEmail().equals(usuario.getEmail())) {
            Optional<Usuario> usuarioComMesmoEmail = usuarioRepository.findByEmail(usuario.getEmail());
            if (usuarioComMesmoEmail.isPresent()) {
                throw new RuntimeException("Erro: Este e-mail já está sendo usado por outro usuário.");
            }
        }

        // Atualiza os campos
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());

        // Atualiza a senha apenas se uma nova senha for fornecida
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        // Atualiza o tipo apenas se for fornecido
        if (usuario.getTipo() != null && !usuario.getTipo().isEmpty()) {
            usuarioExistente.setTipo(usuario.getTipo());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public void excluirUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        return usuario;
    }
}
