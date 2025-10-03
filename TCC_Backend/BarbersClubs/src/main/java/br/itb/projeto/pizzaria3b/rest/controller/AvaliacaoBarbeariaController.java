package br.itb.projeto.pizzaria3b.rest.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.AvaliacaoBarbearia;
import br.itb.projeto.pizzaria3b.model.entity.Barbearia;
import br.itb.projeto.pizzaria3b.model.entity.Cliente;
import br.itb.projeto.pizzaria3b.model.repository.AvaliacaoBarbeariaRepository;
import br.itb.projeto.pizzaria3b.model.repository.BarbeariaRepository;
import br.itb.projeto.pizzaria3b.model.repository.ClienteRepository;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin("*")
public class AvaliacaoBarbeariaController {

    @Autowired
    private AvaliacaoBarbeariaRepository avaliacaoRepository;
    
    @Autowired
    private BarbeariaRepository barbeariaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    // Listar todas as avaliações
    @GetMapping
    public ResponseEntity<List<AvaliacaoBarbearia>> listarAvaliacoes() {
        List<AvaliacaoBarbearia> avaliacoes = avaliacaoRepository.findAll();
        return ResponseEntity.ok(avaliacoes);
    }

    // Buscar avaliações por barbearia
    @GetMapping("/barbearia/{barbeariaId}")
    public ResponseEntity<List<AvaliacaoBarbearia>> buscarAvaliacoesPorBarbearia(@PathVariable Long barbeariaId) {
        if (!barbeariaRepository.existsById(barbeariaId)) {
            return ResponseEntity.notFound().build();
        }
        
        List<AvaliacaoBarbearia> avaliacoes = avaliacaoRepository.findByBarbeariaId(barbeariaId);
        return ResponseEntity.ok(avaliacoes);
    }

    // Buscar avaliações por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AvaliacaoBarbearia>> buscarAvaliacoesPorCliente(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }
        
        List<AvaliacaoBarbearia> avaliacoes = avaliacaoRepository.findByClienteId(clienteId);
        return ResponseEntity.ok(avaliacoes);
    }

    // Criar nova avaliação
    @PostMapping
    public ResponseEntity<?> criarAvaliacao(@RequestBody AvaliacaoBarbearia avaliacao) {
        // Validar barbearia
        Optional<Barbearia> barbearia = barbeariaRepository.findById(avaliacao.getBarbearia().getId());
        if (!barbearia.isPresent()) {
            return ResponseEntity.badRequest().body("Barbearia não encontrada");
        }
        
        // Validar cliente
        Optional<Cliente> cliente = clienteRepository.findById(avaliacao.getCliente().getId());
        if (!cliente.isPresent()) {
            return ResponseEntity.badRequest().body("Cliente não encontrado");
        }
        
        // Definir data da avaliação
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        
        // Salvar avaliação
        AvaliacaoBarbearia novaAvaliacao = avaliacaoRepository.save(avaliacao);
        return ResponseEntity.ok(novaAvaliacao);
    }

    // Excluir avaliação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAvaliacao(@PathVariable Long id) {
        if (!avaliacaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        avaliacaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Calcular média de avaliações de uma barbearia
    @GetMapping("/media/{barbeariaId}")
    public ResponseEntity<?> calcularMediaAvaliacoes(@PathVariable Long barbeariaId) {
        if (!barbeariaRepository.existsById(barbeariaId)) {
            return ResponseEntity.notFound().build();
        }
        
        Double media = avaliacaoRepository.calcularMediaAvaliacoes(barbeariaId);
        if (media == null) {
            media = 0.0;
        }
        
        return ResponseEntity.ok(media);
    }
}