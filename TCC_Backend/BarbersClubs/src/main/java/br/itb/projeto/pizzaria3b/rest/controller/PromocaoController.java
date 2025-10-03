package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@RestController
@RequestMapping("/api/promocoes")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://localhost:53798", "http://localhost:*", "*"})
public class PromocaoController {

    private static List<Map<String, Object>> promocoes = new ArrayList<>();
    private static Long nextId = 1L;
    
    static {
        // Adicionando promoções iniciais
        Map<String, Object> promocao1 = new HashMap<>();
        promocao1.put("id", nextId++);
        promocao1.put("name", "Desconto de Natal");
        promocao1.put("description", "25% de desconto em todos os serviços");
        promocao1.put("discount_type", "percentage");
        promocao1.put("discount_value", 25);
        promocao1.put("start_date", "2024-12-01");
        promocao1.put("end_date", "2024-12-31");
        promocao1.put("min_loyalty_level", "bronze");
        promocoes.add(promocao1);
    }

    @GetMapping
    public ResponseEntity<?> getAllPromocoes() {
        try {
            return ResponseEntity.ok(promocoes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> criarPromocao(@RequestBody Map<String, Object> dados) {
        try {
            Map<String, Object> novaPromocao = new HashMap<>(dados);
            novaPromocao.put("id", nextId++);
            promocoes.add(novaPromocao);
            return ResponseEntity.ok(Map.of("message", "Promoção criada com sucesso!", "success", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Erro: " + e.getMessage(), "success", false));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPromocao(@PathVariable Long id, @RequestBody Map<String, Object> dados) {
        try {
            for (int i = 0; i < promocoes.size(); i++) {
                if (promocoes.get(i).get("id").equals(id)) {
                    Map<String, Object> promocaoAtualizada = new HashMap<>(dados);
                    promocaoAtualizada.put("id", id);
                    promocoes.set(i, promocaoAtualizada);
                    break;
                }
            }
            return ResponseEntity.ok(Map.of("message", "Promoção atualizada com sucesso!", "success", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Erro: " + e.getMessage(), "success", false));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPromocao(@PathVariable Long id) {
        try {
            promocoes.removeIf(p -> p.get("id").equals(id));
            return ResponseEntity.ok(Map.of("message", "Promoção deletada com sucesso!", "success", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Erro: " + e.getMessage(), "success", false));
        }
    }
}