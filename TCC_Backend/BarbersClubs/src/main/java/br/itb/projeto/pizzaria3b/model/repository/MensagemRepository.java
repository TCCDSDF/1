package br.itb.projeto.pizzaria3b.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.itb.projeto.pizzaria3b.model.entity.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    @Query(value = "SELECT id, mensagem, remetente_id, iniciadoEm FROM MensagensChat WHERE remetente_id = :userId OR destinatario_id = :userId ORDER BY iniciadoEm ASC", nativeQuery = true)
    List<Object[]> findMensagensChatByUser(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO MensagensChat (mensagem, remetente_id, destinatario_id) VALUES (:mensagem, :remetenteId, :destinatarioId)", nativeQuery = true)
    void salvarMensagemChat(@Param("mensagem") String mensagem, @Param("remetenteId") int remetenteId, @Param("destinatarioId") int destinatarioId);

}
