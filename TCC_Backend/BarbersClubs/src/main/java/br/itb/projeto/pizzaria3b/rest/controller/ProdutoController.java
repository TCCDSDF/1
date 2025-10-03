package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.repository.BarbeiroRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @GetMapping("/populares")
    public ResponseEntity<?> getPopulares() {
        try {
            List<Object> barbeiros = barbeiroRepository.findAll()
                .stream()
                .map(barbeiro -> {
                    return new Object() {
                        public final Long id = barbeiro.getId();
                        public final String name = barbeiro.getNome();
                        public final String[] specialties = barbeiro.getEspecialidades() != null ? 
                            barbeiro.getEspecialidades().split(", ") : new String[0];
                        public final Double rating = barbeiro.getMediaAvaliacao();
                        public final String imageUrl = "https://images.pexels.com/photos/1222271/pexels-photo-1222271.jpeg?auto=compress&cs=tinysrgb&w=400";
                        public final Double originalPrice = 50.0;
                        public final Double discountPrice = 40.0;
                    };
                })
                .collect(java.util.stream.Collectors.toList());
            
            return ResponseEntity.ok(barbeiros);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/recentes")
    public ResponseEntity<?> getRecentes() {
        return getPopulares(); // Retorna os mesmos dados por simplicidade
    }

    @GetMapping("/destaque")
    public ResponseEntity<?> getDestaque() {
        return getPopulares(); // Retorna os mesmos dados por simplicidade
    }
}