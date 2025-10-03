package br.itb.projeto.pizzaria3b.model.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.itb.projeto.pizzaria3b.model.entity.MensagensChat;

@Repository
public interface MensagensChatRepository extends JpaRepository<MensagensChat, Integer> {
    
    @Query("SELECT m FROM MensagensChat m WHERE m.remetente.id = ?1 OR m.destinatario.id = ?1 ORDER BY m.iniciadoEm ASC")
    List<MensagensChat> findByClienteId(Integer clienteId);
    
    @Query(value = "SELECT COUNT(*) FROM MensagensChat WHERE remetente_id = ?1 AND lida = 0", nativeQuery = true)
    Integer countUnreadMessages(Integer clientId);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE MensagensChat SET lida = 1 WHERE id = ?1", nativeQuery = true)
    void markAsRead(Integer messageId);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO MensagensChat (mensagem, lida, mensagemBot, iniciadoEm, remetente_id, destinatario_id) VALUES (?3, 0, 0, GETDATE(), ?1, ?2)", nativeQuery = true)
    void sendMessage(Integer senderId, Integer receiverId, String message);
}