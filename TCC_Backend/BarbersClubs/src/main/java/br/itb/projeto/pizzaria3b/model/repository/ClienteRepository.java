package br.itb.projeto.pizzaria3b.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.itb.projeto.pizzaria3b.model.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    @Query(value = "SELECT id, nome, email FROM Cliente", nativeQuery = true)
    List<Object[]> findAllClientes();
    
    Cliente findByEmail(String email);

    boolean existsById(Long clienteId);
}