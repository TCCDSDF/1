package br.itb.projeto.pizzaria3b.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.itb.projeto.pizzaria3b.model.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT id, nome, descricao, duracao, preco, image_url FROM Servico WHERE ativo = 1", nativeQuery = true)
    List<Object[]> findAllServicos();
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Servico (nome, descricao, duracao, preco, categoria, ativo, image_url) VALUES (?1, ?2, ?3, ?4, ?5, 1, ?6)", nativeQuery = true)
    void inserirServico(String nome, String descricao, int duracao, double preco, String categoria, String imageUrl);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE Servico SET nome = ?2, descricao = ?3, duracao = ?4, preco = ?5, categoria = ?6, image_url = ?7 WHERE id = ?1", nativeQuery = true)
    void atualizarServico(int id, String nome, String descricao, int duracao, double preco, String categoria, String imageUrl);

}
