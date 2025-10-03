package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.Barbeiro;
import br.itb.projeto.pizzaria3b.model.repository.BarbeiroRepository;

@RestController
@RequestMapping("/api/barbeiros")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "*"})
public class BarbeiroController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private BarbeiroRepository barbeiroRepository;
    
    @GetMapping
    public ResponseEntity<List<Barbeiro>> listarBarbeiros() {
        try {
            List<Barbeiro> barbeiros = barbeiroRepository.findAll();
            System.out.println("Retornando " + barbeiros.size() + " barbeiros");
            
            // Se não houver barbeiros no banco, criar alguns exemplos
            if (barbeiros.isEmpty()) {
                System.out.println("Criando barbeiros de exemplo");
                
                // Criar barbeiros de exemplo
                Barbeiro barbeiro1 = new Barbeiro();
                barbeiro1.setNome("Pedro Silva");
                barbeiro1.setEmail("pedro.barbeiro@gmail.com");
                barbeiro1.setSenha("pedro123");
                barbeiro1.setBiografia("Especialista em cortes modernos e degradês");
                barbeiro1.setEspecialidades("Corte Masculino, Barba, Degradê, Navalhado");
                barbeiro1.setTempoExperiencia(5);
                barbeiro1.setMediaAvaliacao(4.8);
                barbeiro1.setDisponibilidade(true);
                barbeiroRepository.save(barbeiro1);
                
                Barbeiro barbeiro2 = new Barbeiro();
                barbeiro2.setNome("João Santos");
                barbeiro2.setEmail("joao.barbeiro@hotmail.com");
                barbeiro2.setSenha("joao456");
                barbeiro2.setBiografia("Mestre da barba tradicional");
                barbeiro2.setEspecialidades("Barba Tradicional, Bigode, Corte Clássico");
                barbeiro2.setTempoExperiencia(8);
                barbeiro2.setMediaAvaliacao(4.9);
                barbeiro2.setDisponibilidade(true);
                barbeiroRepository.save(barbeiro2);
                
                Barbeiro barbeiro3 = new Barbeiro();
                barbeiro3.setNome("Lucas Costa");
                barbeiro3.setEmail("lucas.barbeiro@yahoo.com");
                barbeiro3.setSenha("lucas789");
                barbeiro3.setBiografia("Cortes criativos e modernos");
                barbeiro3.setEspecialidades("Corte Moderno, Coloração, Styling, Penteados");
                barbeiro3.setTempoExperiencia(3);
                barbeiro3.setMediaAvaliacao(4.7);
                barbeiro3.setDisponibilidade(true);
                barbeiroRepository.save(barbeiro3);
                
                // Buscar novamente
                barbeiros = barbeiroRepository.findAll();
            }
            
            // Imprimir os dados para debug
            for (Barbeiro barbeiro : barbeiros) {
                System.out.println("Barbeiro: " + barbeiro.getNome() + ", ID: " + barbeiro.getId());
            }
            
            return ResponseEntity.ok(barbeiros);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> criarBarbeiro(@RequestBody Barbeiro barbeiro) {
        try {
            Barbeiro novoBarbeiro = barbeiroRepository.save(barbeiro);
            System.out.println("Barbeiro criado: " + novoBarbeiro.getNome() + ", ID: " + novoBarbeiro.getId());
            return ResponseEntity.ok(novoBarbeiro);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao criar barbeiro: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarBarbeiro(@PathVariable Long id) {
        try {
            System.out.println("Tentando deletar barbeiro com ID: " + id);
            
            // Verificar se o barbeiro existe
            if (!barbeiroRepository.existsById(id)) {
                System.out.println("Barbeiro não encontrado com ID: " + id);
                return ResponseEntity.status(404).body(Map.of("message", "Barbeiro não encontrado"));
            }
            
            // Usar SQL direto para evitar problemas com chaves estrangeiras
            try {
                // Deletar agendamentos associados primeiro
                String deleteAgendamentosSql = "DELETE FROM Agendamento WHERE barbeiro_id = ?";
                int agendamentosDeleted = jdbcTemplate.update(deleteAgendamentosSql, id);
                System.out.println("Deletados " + agendamentosDeleted + " agendamentos associados ao barbeiro ID: " + id);
                
                // Deletar o barbeiro usando SQL direto
                String deleteBarbeiroSql = "DELETE FROM Barbeiro WHERE id = ?";
                int result = jdbcTemplate.update(deleteBarbeiroSql, id);
                
                if (result > 0) {
                    System.out.println("Barbeiro deletado com sucesso, ID: " + id);
                    return ResponseEntity.ok(Map.of("message", "Barbeiro deletado com sucesso"));
                } else {
                    System.out.println("Falha ao deletar barbeiro, ID: " + id);
                    return ResponseEntity.status(500).body(Map.of("error", "Falha ao deletar barbeiro"));
                }
            } catch (Exception e) {
                System.out.println("Erro ao executar SQL para deletar barbeiro: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(500).body(Map.of("error", "Erro ao deletar barbeiro: " + e.getMessage()));
            }
        } catch (Exception e) {
            System.out.println("Erro geral ao deletar barbeiro: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao deletar barbeiro: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarBarbeiro(@PathVariable Long id, @RequestBody Barbeiro barbeiro) {
        try {
            System.out.println("Tentando atualizar barbeiro com ID: " + id);
            
            // Verificar se o barbeiro existe
            if (!barbeiroRepository.existsById(id)) {
                System.out.println("Barbeiro não encontrado com ID: " + id);
                return ResponseEntity.status(404).body(Map.of("message", "Barbeiro não encontrado"));
            }
            
            // Buscar o barbeiro existente
            Barbeiro barbeiroExistente = barbeiroRepository.findById(id).get();
            
            // Atualizar apenas os campos fornecidos
            if (barbeiro.getNome() != null) barbeiroExistente.setNome(barbeiro.getNome());
            if (barbeiro.getBiografia() != null) barbeiroExistente.setBiografia(barbeiro.getBiografia());
            if (barbeiro.getEspecialidades() != null) barbeiroExistente.setEspecialidades(barbeiro.getEspecialidades());
            if (barbeiro.getTempoExperiencia() != null) barbeiroExistente.setTempoExperiencia(barbeiro.getTempoExperiencia());
            if (barbeiro.getDisponibilidade() != null) barbeiroExistente.setDisponibilidade(barbeiro.getDisponibilidade());
            
            // Salvar as alterações
            Barbeiro barbeiroAtualizado = barbeiroRepository.save(barbeiroExistente);
            System.out.println("Barbeiro atualizado com sucesso, ID: " + id);
            
            return ResponseEntity.ok(barbeiroAtualizado);
        } catch (Exception e) {
            System.out.println("Erro ao atualizar barbeiro: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao atualizar barbeiro: " + e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String senha = credentials.get("senha");
            
            System.out.println("Tentativa de login de barbeiro: " + email);
            
            // Verificar se o barbeiro existe
            String sql = "SELECT id, nome, email FROM Barbeiro WHERE email = ? AND senha = ?";
            Map<String, Object> barbeiro = jdbcTemplate.queryForMap(sql, email, senha);
            
            if (barbeiro != null) {
                // Gerar token (simplificado)
                String token = "token-" + System.currentTimeMillis();
                
                // Criar resposta
                Map<String, Object> response = new HashMap<>();
                response.put("id", barbeiro.get("id"));
                response.put("nome", barbeiro.get("nome"));
                response.put("email", barbeiro.get("email"));
                response.put("tipo", "barbeiro");
                response.put("token", token);
                
                System.out.println("Login de barbeiro bem-sucedido: " + barbeiro.get("nome"));
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body(Map.of("message", "Credenciais inválidas"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(Map.of("message", "Credenciais inválidas"));
        }
    }
}