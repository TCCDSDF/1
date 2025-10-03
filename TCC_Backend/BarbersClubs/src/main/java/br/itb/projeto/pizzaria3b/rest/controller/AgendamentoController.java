package br.itb.projeto.pizzaria3b.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import br.itb.projeto.pizzaria3b.model.repository.AgendamentoRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    private static List<Map<String, Object>> agendamentosMemoria = new ArrayList<>();
    private static Long nextId = 1L;

    @GetMapping
    public ResponseEntity<?> getAllAgendamentos() {
        try {
            List<Object[]> resultados = agendamentoRepository.findAllAgendamentosWithDetails();
            List<Map<String, Object>> agendamentos = resultados.stream()
                .map(row -> Map.of(
                    "id", row[0],
                    "dataAgendamento", row[1],
                    "statusAgendamento", row[2],
                    "clienteNome", row[3],
                    "servicoNome", row[4],
                    "barbeiroNome", row[5]
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar agendamentos: " + e.getMessage());
        }
    }

    @GetMapping("/barbeiro/{barbeiroId}")
    public ResponseEntity<?> getAgendamentosByBarbeiro(@PathVariable Long barbeiroId) {
        try {
            List<Object[]> agendamentos = agendamentoRepository.findAgendamentosByBarbeiroId(barbeiroId);
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar agendamentos: " + e.getMessage());
        }
    }

    @GetMapping("/ultimo-corte/{barbeiroId}")
    public ResponseEntity<?> getUltimoCorte(@PathVariable Long barbeiroId) {
        try {
            Object[] ultimoCorte = agendamentoRepository.findUltimoCorteByBarbeiroId(barbeiroId);
            if (ultimoCorte != null) {
                return ResponseEntity.ok(ultimoCorte);
            } else {
                Object[] ultimoAgendamento = agendamentoRepository.findUltimoAgendamentoByBarbeiroId(barbeiroId);
                return ResponseEntity.ok(ultimoAgendamento);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar último corte: " + e.getMessage());
        }
    }

    @GetMapping("/pendentes/{barbeiroId}")
    public ResponseEntity<?> getAgendamentosPendentes(@PathVariable Long barbeiroId) {
        try {
            List<Object[]> agendamentos = agendamentoRepository.findAgendamentosPendentesByBarbeiroId(barbeiroId);
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar agendamentos pendentes: " + e.getMessage());
        }
    }

    @GetMapping("/confirmados/{barbeiroId}")
    public ResponseEntity<?> getAgendamentosConfirmados(@PathVariable Long barbeiroId) {
        try {
            List<Object[]> agendamentos = agendamentoRepository.findAgendamentosConfirmadosByBarbeiroId(barbeiroId);
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar agendamentos confirmados: " + e.getMessage());
        }
    }

    @PutMapping("/confirmar/{agendamentoId}")
    public ResponseEntity<?> confirmarAgendamento(@PathVariable Long agendamentoId) {
        try {
            agendamentoRepository.updateStatusAgendamento(agendamentoId, "Completo");
            return ResponseEntity.ok(Map.of(
                "message", "Agendamento concluído com sucesso",
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Erro ao confirmar agendamento: " + e.getMessage(),
                "success", false
            ));
        }
    }

    @PutMapping("/rejeitar/{agendamentoId}")
    public ResponseEntity<?> rejeitarAgendamento(@PathVariable Long agendamentoId) {
        try {
            agendamentoRepository.updateStatusAgendamento(agendamentoId, "Cancelado");
            return ResponseEntity.ok(Map.of(
                "message", "Agendamento rejeitado com sucesso",
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Erro ao rejeitar agendamento: " + e.getMessage(),
                "success", false
            ));
        }
    }

    @PostMapping
    public ResponseEntity<?> criarAgendamento(@RequestBody Map<String, Object> agendamento) {
        try {
            agendamentoRepository.criarAgendamento(
                (Integer) agendamento.get("servico_id"),
                (Integer) agendamento.get("barbeiro_id")
            );
            return ResponseEntity.ok(Map.of(
                "message", "Agendamento criado com sucesso",
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Erro ao criar agendamento: " + e.getMessage(),
                "success", false
            ));
        }
    }
    

}