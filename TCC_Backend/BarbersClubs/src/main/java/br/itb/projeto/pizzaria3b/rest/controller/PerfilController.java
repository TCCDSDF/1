package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.Cliente;
import br.itb.projeto.pizzaria3b.model.repository.ClienteRepository;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "*"})
public class PerfilController {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPerfil(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteRepository.findById(id).orElse(null);
            if (cliente == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(Map.of(
                "id", cliente.getId(),
                "nome", cliente.getNome(),
                "email", cliente.getEmail(),
                "telefone", cliente.getTelefone() != null ? cliente.getTelefone() : "",
                "nivelFidelidade", cliente.getNivelFidelidade() != null ? cliente.getNivelFidelidade() : "Bronze"
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao buscar perfil: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerfil(@PathVariable Integer id, @RequestBody Map<String, Object> dados) {
        try {
            Cliente cliente = clienteRepository.findById(id).orElse(null);
            if (cliente == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Atualizar dados
            if (dados.containsKey("nome")) {
                cliente.setNome((String) dados.get("nome"));
            }
            
            if (dados.containsKey("email")) {
                cliente.setEmail((String) dados.get("email"));
            }
            
            if (dados.containsKey("telefone")) {
                cliente.setTelefone((String) dados.get("telefone"));
            }
            
            if (dados.containsKey("senha")) {
                cliente.setSenha((String) dados.get("senha"));
            }
            
            // Salvar alterações
            clienteRepository.save(cliente);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Perfil atualizado com sucesso"
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao atualizar perfil: " + e.getMessage());
        }
    }
}