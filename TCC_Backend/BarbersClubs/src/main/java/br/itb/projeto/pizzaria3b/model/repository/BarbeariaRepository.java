package br.itb.projeto.pizzaria3b.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.itb.projeto.pizzaria3b.model.entity.Barbearia;

@Repository
public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {
    
    // Buscar barbearias parceiras ativas
    List<Barbearia> findByParceiraAndAtivo(Boolean parceira, Boolean ativo);
    
    // Buscar barbearias por nome contendo o termo de busca
    List<Barbearia> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar barbearias por endereço contendo o termo de busca
    List<Barbearia> findByEnderecoContainingIgnoreCase(String endereco);
    
    // Buscar barbearias próximas usando a fórmula de Haversine
    @Query(value = "SELECT b.*, " +
           "(6371 * acos(cos(radians(:latitude)) * cos(radians(b.latitude)) * cos(radians(b.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(b.latitude)))) AS distancia " +
           "FROM Barbearia b " +
           "WHERE b.parceira = true AND b.ativo = true " +
           "HAVING distancia <= :raio " +
           "ORDER BY distancia", 
           nativeQuery = true)
    List<Barbearia> findBarbeariasPorLocalizacao(
            @Param("latitude") Double latitude, 
            @Param("longitude") Double longitude, 
            @Param("raio") Integer raio);
}