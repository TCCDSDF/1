package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.Cliente;
import br.itb.projeto.pizzaria3b.model.repository.ClienteRepository;

@RestController
@RequestMapping("/api/loyalty")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "*"})
public class LoyaltyController {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping("/points")
    public ResponseEntity<?> getLoyaltyLevels() {
        try {
            List<Map<String, Object>> levels = new ArrayList<>();
            
            // Níveis de fidelidade
            levels.add(Map.of(
                "id", 1,
                "name", "Bronze",
                "pointsRequired", 0,
                "discount", 0,
                "color", "#CD7F32"
            ));
            
            levels.add(Map.of(
                "id", 2,
                "name", "Prata",
                "pointsRequired", 100,
                "discount", 5,
                "color", "#C0C0C0"
            ));
            
            levels.add(Map.of(
                "id", 3,
                "name", "Ouro",
                "pointsRequired", 300,
                "discount", 10,
                "color", "#FFD700"
            ));
            
            levels.add(Map.of(
                "id", 4,
                "name", "Platina",
                "pointsRequired", 600,
                "discount", 15,
                "color", "#E5E4E2"
            ));
            
            levels.add(Map.of(
                "id", 5,
                "name", "Diamante",
                "pointsRequired", 1000,
                "discount", 20,
                "color", "#B9F2FF"
            ));
            
            return ResponseEntity.ok(levels);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao buscar níveis de fidelidade: " + e.getMessage());
        }
    }
    
    @GetMapping("/points/{userId}")
    public ResponseEntity<?> getUserLoyaltyPoints(@PathVariable Integer userId) {
        try {
            Cliente cliente = clienteRepository.findById(userId).orElse(null);
            
            if (cliente == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Dados de fidelidade do cliente
            Map<String, Object> loyaltyData = new HashMap<>();
            loyaltyData.put("userId", cliente.getId());
            loyaltyData.put("points", 150); // Valor fixo para exemplo
            loyaltyData.put("level", cliente.getNivelFidelidade() != null ? cliente.getNivelFidelidade() : "Bronze");
            loyaltyData.put("nextLevel", "Ouro");
            loyaltyData.put("pointsToNextLevel", 150);
            loyaltyData.put("discount", 5);
            
            return ResponseEntity.ok(loyaltyData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao buscar pontos de fidelidade: " + e.getMessage());
        }
    }
}