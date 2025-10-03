package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.repository.CategoriaRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<?> getCategorias() {
        try {
            List<Object> categorias = categoriaRepository.findDistinctCategorias()
                .stream()
                .map(categoria -> {
                    return new Object() {
                        public final String nome = categoria;
                        public final String descricao = "Categoria " + categoria;
                    };
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar categorias: " + e.getMessage());
        }
    }
}