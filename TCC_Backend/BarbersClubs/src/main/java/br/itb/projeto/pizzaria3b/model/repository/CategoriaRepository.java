package br.itb.projeto.pizzaria3b.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.itb.projeto.pizzaria3b.model.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    @Query("SELECT DISTINCT c.categoria FROM Categoria c WHERE c.categoria IS NOT NULL")
    List<String> findDistinctCategorias();
}