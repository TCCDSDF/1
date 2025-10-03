package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "*"})
public class HealthController {

    @GetMapping
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "message", "API está funcionando corretamente"
        ));
    }
}