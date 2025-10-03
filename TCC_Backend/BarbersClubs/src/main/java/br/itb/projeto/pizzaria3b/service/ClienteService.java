package br.itb.projeto.pizzaria3b.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.itb.projeto.pizzaria3b.model.entity.Cliente;
import br.itb.projeto.pizzaria3b.model.repository.ClienteRepository;
import br.itb.projeto.pizzaria3b.rest.exception.ResourceNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Cliente save(Cliente cliente) {
        // Verificar se o email já existe
        Cliente existingUser = clienteRepository.findByEmail(cliente.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        
        // Definir valores padrão
        cliente.setFotoPerfil(null); // Não usar fotoPerfil no cadastro inicial
        cliente.setNivelFidelidade("Bronze");
        cliente.setCriadoEm(LocalDateTime.now());
        cliente.setAtualizadoEm(LocalDateTime.now());
        
        return clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente) {
        // Verificar se o cliente existe
        Cliente existingUser = findById(cliente.getId());
        
        // Atualizar os campos
        existingUser.setNome(cliente.getNome());
        existingUser.setEmail(cliente.getEmail());
        if (cliente.getSenha() != null && !cliente.getSenha().isEmpty()) {
            existingUser.setSenha(cliente.getSenha());
        }
        existingUser.setTelefone(cliente.getTelefone());
        existingUser.setAtualizadoEm(LocalDateTime.now());
        
        return clienteRepository.save(existingUser);
    }

    public void delete(Integer id) {
        Cliente cliente = findById(id);
        clienteRepository.delete(cliente);
    }
}