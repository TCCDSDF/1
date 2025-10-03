package br.itb.projeto.pizzaria3b.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.itb.projeto.pizzaria3b.model.entity.AvaliacaoBarbearia;

@Repository
public interface AvaliacaoBarbeariaRepository extends JpaRepository<AvaliacaoBarbearia, Long> {
    
    // Buscar avaliações por barbearia
    List<AvaliacaoBarbearia> findByBarbeariaId(Long barbeariaId);
    
    // Buscar avaliações por cliente
    List<AvaliacaoBarbearia> findByClienteId(Long clienteId);
    
    // Calcular média de avaliações por barbearia
    @Query("SELECT AVG(a.nota) FROM AvaliacaoBarbearia a WHERE a.barbearia.id = :barbeariaId")
    Double calcularMediaAvaliacoes(@Param("barbeariaId") Long barbeariaId);
}