package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import br.itb.projeto.pizzaria3b.model.entity.Cliente;
import br.itb.projeto.pizzaria3b.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "*"})
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }
    
    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.save(cliente);
            return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            cliente.setId(id);
            Cliente clienteAtualizado = clienteService.update(cliente);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}