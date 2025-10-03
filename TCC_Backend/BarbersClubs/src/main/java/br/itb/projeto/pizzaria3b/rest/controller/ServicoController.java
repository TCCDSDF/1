package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.Produto;
import br.itb.projeto.pizzaria3b.model.repository.ProdutoRepository;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://localhost:53798", "*"})
@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> findAll() {
        try {
            // Buscar dados reais da tabela Servico
            List<Object[]> resultados = produtoRepository.findAllServicos();
            List<Map<String, Object>> servicos = resultados.stream()
                .map(row -> {
                    // Criar mapa com valores básicos
                    Map<String, Object> servico = new java.util.HashMap<>();
                    servico.put("id", row[0]);
                    servico.put("nome", row[1]);
                    servico.put("descricao", row[2] != null ? row[2] : "");
                    servico.put("duracao", row[3]);
                    servico.put("preco", row[4]);
                    
                    // Adicionar URL da imagem se disponível
                    if (row.length > 5 && row[5] != null) {
                        servico.put("image_url", row[5]);
                    } else {
                        servico.put("image_url", "");
                    }
                    
                    return servico;
                })
                .collect(java.util.stream.Collectors.toList());
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            // Fallback para dados de teste se houver erro
            List<Map<String, Object>> servicos = Arrays.asList(
                Map.of("id", 1, "nome", "Corte Social", "descricao", "Corte de cabelo Premium", "preco", 45.0, "duracao", 45),
                Map.of("id", 2, "nome", "Barba Navalhada", "descricao", "Barba tradicional", "preco", 35.0, "duracao", 30),
                Map.of("id", 3, "nome", "Barba Esculpida", "descricao", "Corte e modelagem de barba", "preco", 30.0, "duracao", 30),
                Map.of("id", 4, "nome", "Pacote VIP", "descricao", "Corte + barba + tratamento", "preco", 89.0, "duracao", 90)
            );
            return ResponseEntity.ok(servicos);
        }
    }
    
    // Método auxiliar para mapear categorias
    private String mapearCategoria(String categoria) {
        if (categoria == null) {
            return "Corte de cabelo"; // Valor padrão
        }
        
        switch(categoria) {
            case "haircut":
                return "Corte de cabelo";
            case "beard":
                return "Barba";
            case "combo":
                return "Combo";
            default:
                return "Especial";
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createServico(@RequestBody Map<String, Object> servico) {
        try {
            // Executar SQL direto para inserir na tabela Servico
            String nome = (String) servico.get("nome");
            String descricao = (String) servico.get("descricao");
            
            int duracao = 30; // valor padrão
            if (servico.get("duracao") != null) {
                duracao = Integer.parseInt(servico.get("duracao").toString());
            }
            
            double preco = 0.0; // valor padrão
            if (servico.get("preco") != null) {
                preco = Double.parseDouble(servico.get("preco").toString());
            }
            
            String categoria = (String) servico.get("categoria");
            
            // Mapear categoria do frontend para o formato do banco
            String categoriaBanco = mapearCategoria(categoria);
            
            // Obter a URL da imagem
            String imageUrl = (String) servico.get("image_url");
            if (imageUrl == null) {
                imageUrl = "";
            }
            
            // Inserir usando SQL nativo
            produtoRepository.inserirServico(nome, descricao, duracao, preco, categoriaBanco, imageUrl);
            
            return ResponseEntity.ok(Map.of(
                "message", "Serviço criado com sucesso",
                "success", true
            ));
        } catch (Exception e) {
            e.printStackTrace(); // Log do erro
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Erro ao criar serviço: " + e.getMessage(),
                "success", false
            ));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateServico(@PathVariable int id, @RequestBody Map<String, Object> servico) {
        try {
            // Extrair dados do serviço com verificações de nulos
            String nome = (String) servico.get("nome");
            String descricao = (String) servico.get("descricao");
            
            int duracao = 30; // valor padrão
            if (servico.get("duracao") != null) {
                duracao = Integer.parseInt(servico.get("duracao").toString());
            }
            
            double preco = 0.0; // valor padrão
            if (servico.get("preco") != null) {
                preco = Double.parseDouble(servico.get("preco").toString());
            }
            
            String categoria = (String) servico.get("categoria");
            
            // Mapear categoria
            String categoriaBanco = mapearCategoria(categoria);
            
            // Obter a URL da imagem
            String imageUrl = (String) servico.get("image_url");
            if (imageUrl == null) {
                imageUrl = "";
            }
            
            // Atualizar serviço
            produtoRepository.atualizarServico(id, nome, descricao, duracao, preco, categoriaBanco, imageUrl);
            
            return ResponseEntity.ok(Map.of(
                "message", "Serviço atualizado com sucesso",
                "success", true
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Erro ao atualizar serviço: " + e.getMessage(),
                "success", false
            ));
        }
    }
}