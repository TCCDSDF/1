package br.itb.projeto.pizzaria3b.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.itb.projeto.pizzaria3b.model.entity.Barbeiro;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

    @Query(value = "SELECT id, nome, email, biografia, especialidades, tempoExperiencia, mediaAvaliacao, disponibilidade FROM Barbeiro", nativeQuery = true)
    List<Object[]> findAllBarbeiros();
    
    Optional<Barbeiro> findByEmail(String email);
}