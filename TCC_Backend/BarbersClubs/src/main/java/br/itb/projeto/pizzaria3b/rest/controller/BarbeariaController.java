package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.Barbearia;
import br.itb.projeto.pizzaria3b.model.repository.BarbeariaRepository;
import br.itb.projeto.pizzaria3b.model.repository.AvaliacaoBarbeariaRepository;

@RestController
@RequestMapping("/api/barbearias")
@CrossOrigin("*")
public class BarbeariaController {

    @Autowired
    private BarbeariaRepository barbeariaRepository;
    
    @Autowired
    private AvaliacaoBarbeariaRepository avaliacaoRepository;

    // Listar todas as barbearias
    @GetMapping
    public ResponseEntity<List<Barbearia>> listarBarbearias() {
        List<Barbearia> barbearias = barbeariaRepository.findAll();
        return ResponseEntity.ok(barbearias);
    }

    // Listar apenas barbearias parceiras ativas
    @GetMapping("/parceiras")
    public ResponseEntity<List<Barbearia>> listarBarbeariasParceiras() {
        List<Barbearia> barbearias = barbeariaRepository.findByParceiraAndAtivo(true, true);
        
        // Adicionar média de avaliações para cada barbearia
        barbearias.forEach(barbearia -> {
            Double mediaAvaliacao = avaliacaoRepository.calcularMediaAvaliacoes(barbearia.getId());
            if (mediaAvaliacao == null) {
                mediaAvaliacao = 0.0;
            }
            // Como não temos um campo mediaAvaliacao na entidade, podemos adicionar via DTO
            // ou retornar um objeto que contenha essa informação
        });
        
        return ResponseEntity.ok(barbearias);
    }

    // Buscar barbearia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Barbearia> buscarBarbeariaPorId(@PathVariable Long id) {
        Optional<Barbearia> barbearia = barbeariaRepository.findById(id);
        
        if (barbearia.isPresent()) {
            return ResponseEntity.ok(barbearia.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar barbearias próximas por coordenadas
    @GetMapping("/proximas")
    public ResponseEntity<List<Barbearia>> buscarBarbeariasPorLocalizacao(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "10") Integer raio) {
        
        try {
            List<Barbearia> barbeariasProximas = barbeariaRepository.findBarbeariasPorLocalizacao(latitude, longitude, raio);
            return ResponseEntity.ok(barbeariasProximas);
        } catch (Exception e) {
            // Fallback para busca simples se a consulta nativa falhar
            List<Barbearia> todasBarbearias = barbeariaRepository.findByParceiraAndAtivo(true, true);
            return ResponseEntity.ok(todasBarbearias);
        }
    }

    // Criar nova barbearia
    @PostMapping
    public ResponseEntity<Barbearia> criarBarbearia(@RequestBody Barbearia barbearia) {
        Barbearia novaBarbearia = barbeariaRepository.save(barbearia);
        return ResponseEntity.ok(novaBarbearia);
    }

    // Atualizar barbearia
    @PutMapping("/{id}")
    public ResponseEntity<Barbearia> atualizarBarbearia(
            @PathVariable Long id,
            @RequestBody Barbearia barbearia) {
        
        if (!barbeariaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        barbearia.setId(id);
        Barbearia barbeariaAtualizada = barbeariaRepository.save(barbearia);
        return ResponseEntity.ok(barbeariaAtualizada);
    }

    // Excluir barbearia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirBarbearia(@PathVariable Long id) {
        if (!barbeariaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        barbeariaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Buscar barbearias por nome
    @GetMapping("/busca")
    public ResponseEntity<List<Barbearia>> buscarBarbearias(@RequestParam String termo) {
        List<Barbearia> barbearias = barbeariaRepository.findByNomeContainingIgnoreCase(termo);
        if (barbearias.isEmpty()) {
            barbearias = barbeariaRepository.findByEnderecoContainingIgnoreCase(termo);
        }
        return ResponseEntity.ok(barbearias);
    }
}