package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.Admin;
import br.itb.projeto.pizzaria3b.model.entity.Barbeiro;
import br.itb.projeto.pizzaria3b.model.entity.Cliente;
import br.itb.projeto.pizzaria3b.model.entity.Usuario;
import br.itb.projeto.pizzaria3b.model.repository.AdminRepository;
import br.itb.projeto.pizzaria3b.model.repository.BarbeiroRepository;
import br.itb.projeto.pizzaria3b.service.ClienteService;
import br.itb.projeto.pizzaria3b.service.UsuarioService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private BarbeiroRepository barbeiroRepository;
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> create(@RequestBody Map<String, String> userData) {
        try {
            // Verificar o tipo de usuário
            String tipo = userData.get("tipo");
            
            if ("cliente".equalsIgnoreCase(tipo)) {
                // Criar um cliente
                Cliente cliente = new Cliente();
                cliente.setNome(userData.get("nome"));
                cliente.setEmail(userData.get("email"));
                cliente.setSenha(userData.get("senha"));
                cliente.setTelefone(userData.get("telefone"));
                
                Cliente novoCliente = clienteService.save(cliente);
                
                Map<String, Object> response = new HashMap<>();
                response.put("id", novoCliente.getId());
                response.put("nome", novoCliente.getNome());
                response.put("email", novoCliente.getEmail());
                response.put("tipo", "cliente");
                response.put("token", "token-" + System.currentTimeMillis());
                
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                // Para outros tipos, usar o serviço de usuário existente
                Usuario usuario = new Usuario();
                usuario.setNome(userData.get("nome"));
                usuario.setEmail(userData.get("email"));
                usuario.setSenha(userData.get("senha"));
                
                Usuario novoUsuario = usuarioService.save(usuario);
                return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");
        
        // Buscar primeiro em Cliente
        Usuario cliente = usuarioService.findByEmailAndSenha(email, senha);
        if (cliente != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("token", "token-" + System.currentTimeMillis());
            response.put("id", cliente.getId());
            response.put("nome", cliente.getNome());
            response.put("email", cliente.getEmail());
            response.put("tipo", "cliente");
            return ResponseEntity.ok(response);
        }
        
        // Buscar em Admin
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && admin.get().getSenha().equals(senha)) {
            Map<String, Object> response = new HashMap<>();
            response.put("token", "token-" + System.currentTimeMillis());
            response.put("id", admin.get().getId());
            response.put("nome", admin.get().getNome());
            response.put("email", admin.get().getEmail());
            response.put("tipo", "admin");
            return ResponseEntity.ok(response);
        }
        
        // Buscar em Barbeiro
        Optional<Barbeiro> barbeiro = barbeiroRepository.findByEmail(email);
        if (barbeiro.isPresent() && barbeiro.get().getSenha().equals(senha)) {
            Map<String, Object> response = new HashMap<>();
            response.put("token", "token-" + System.currentTimeMillis());
            response.put("id", barbeiro.get().getId());
            response.put("nome", barbeiro.get().getNome());
            response.put("email", barbeiro.get().getEmail());
            response.put("tipo", "barbeiro");
            response.put("especialidades", barbeiro.get().getEspecialidades());
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioAtualizado = usuarioService.update(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken() {
        Map<String, Object> response = new HashMap<>();
        response.put("valid", true);
        return ResponseEntity.ok(response);
    }
}