package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.Admin;
import br.itb.projeto.pizzaria3b.model.entity.Barbeiro;
import br.itb.projeto.pizzaria3b.model.entity.Usuario;
import br.itb.projeto.pizzaria3b.model.repository.AdminRepository;
import br.itb.projeto.pizzaria3b.model.repository.BarbeiroRepository;
import br.itb.projeto.pizzaria3b.service.UsuarioService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private BarbeiroRepository barbeiroRepository;

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
            response.put("userType", "cliente");
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
            response.put("userType", "admin");
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
            response.put("userType", "barbeiro");
            response.put("especialidades", barbeiro.get().getEspecialidades());
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.save(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }
    
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken() {
        Map<String, Object> response = new HashMap<>();
        response.put("valid", true);
        return ResponseEntity.ok(response);
    }
}