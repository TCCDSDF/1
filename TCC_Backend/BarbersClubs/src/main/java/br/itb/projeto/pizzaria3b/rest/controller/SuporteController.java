package br.itb.projeto.pizzaria3b.rest.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itb.projeto.pizzaria3b.model.entity.Cliente;
import br.itb.projeto.pizzaria3b.model.entity.MensagensChat;
import br.itb.projeto.pizzaria3b.model.repository.ClienteRepository;
import br.itb.projeto.pizzaria3b.model.repository.MensagensChatRepository;

@RestController
@RequestMapping("/api/suporte")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "*"})
public class SuporteController {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private MensagensChatRepository mensagemRepository;
    
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensagem(@RequestBody Map<String, Object> dados) {
        try {
            Integer clienteId = Integer.parseInt(dados.get("cliente_id").toString());
            String mensagem = (String) dados.get("mensagem");
            String assunto = (String) dados.get("assunto");
            
            // Buscar cliente
            Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
            if (cliente == null) {
                return ResponseEntity.badRequest().body("Cliente não encontrado");
            }
            
            // Buscar admin (destinatário)
            Cliente admin = clienteRepository.findById(1).orElse(null);
            if (admin == null) {
                return ResponseEntity.badRequest().body("Admin não encontrado");
            }
            
            // Criar mensagem com assunto
            MensagensChat mensagemChat = new MensagensChat();
            mensagemChat.setMensagem("[" + assunto + "] " + mensagem);
            mensagemChat.setLida(false);
            mensagemChat.setMensagemBot(false);
            mensagemChat.setIniciadoEm(LocalDateTime.now());
            mensagemChat.setRemetente(cliente);
            mensagemChat.setDestinatario(admin);
            
            mensagemRepository.save(mensagemChat);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Mensagem enviada com sucesso"
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar mensagem: " + e.getMessage());
        }
    }
}