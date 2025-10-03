package br.itb.projeto.pizzaria3b.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.itb.projeto.pizzaria3b.model.entity.Usuario;
import br.itb.projeto.pizzaria3b.model.repository.UsuarioRepository;
import br.itb.projeto.pizzaria3b.rest.exception.ResourceNotFoundException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    public Usuario findByEmailAndSenha(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            return usuario.get();
        }
        return null;
    }

    public Usuario save(Usuario usuario) {
        // Verificar se o email já existe
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        // Verificar se o usuário existe
        Usuario existingUser = findById(usuario.getId());
        
        // Atualizar os campos
        existingUser.setNome(usuario.getNome());
        existingUser.setEmail(usuario.getEmail());
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            existingUser.setSenha(usuario.getSenha());
        }
        existingUser.setFotoUrl(usuario.getFotoUrl());
        
        return usuarioRepository.save(existingUser);
    }

    public void delete(Long id) {
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }
}