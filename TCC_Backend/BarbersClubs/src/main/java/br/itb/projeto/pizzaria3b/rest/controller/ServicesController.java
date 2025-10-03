package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "*"})
public class ServicesController {

    @GetMapping
    public ResponseEntity<?> getAllServices() {
        try {
            List<Map<String, Object>> services = new ArrayList<>();
            
            // Serviços de exemplo
            services.add(Map.of(
                "id", 1,
                "name", "Corte de Cabelo",
                "description", "Corte profissional com tesoura e máquina",
                "price", 45.00,
                "duration", 30,
                "image", "https://images.unsplash.com/photo-1599351431202-1e0f0137899a?q=80&w=1000&auto=format&fit=crop"
            ));
            
            services.add(Map.of(
                "id", 2,
                "name", "Barba",
                "description", "Aparar e modelar barba com toalha quente",
                "price", 35.00,
                "duration", 25,
                "image", "https://images.unsplash.com/photo-1621605810052-80936544da1c?q=80&w=1000&auto=format&fit=crop"
            ));
            
            services.add(Map.of(
                "id", 3,
                "name", "Corte + Barba",
                "description", "Combo de corte de cabelo e barba",
                "price", 70.00,
                "duration", 50,
                "image", "https://images.unsplash.com/photo-1503951914875-452162b0f3f1?q=80&w=1000&auto=format&fit=crop"
            ));
            
            services.add(Map.of(
                "id", 4,
                "name", "Coloração",
                "description", "Pintura de cabelo com produtos de qualidade",
                "price", 90.00,
                "duration", 60,
                "image", "https://images.unsplash.com/photo-1562322140-8baeececf3df?q=80&w=1000&auto=format&fit=crop"
            ));
            
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao buscar serviços: " + e.getMessage());
        }
    }
}