package br.itb.projeto.pizzaria3b.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.itb.projeto.pizzaria3b.model.entity.Produto;
import br.itb.projeto.pizzaria3b.model.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

	private ProdutoRepository produtoRepository; 
	// Source => Generate Constructor using Fields...
	public ProdutoService(ProdutoRepository produtoRepository) {
		super();
		this.produtoRepository = produtoRepository;
	}
	
	public Produto findById(long id) {
		Optional <Produto> produto = produtoRepository.findById(id);
		if(produto.isPresent()) {
			return produto.get();
		}
		return null;
	}
	
	public List<Produto> findAll(){
		List<Produto> promocoes = produtoRepository.findAll();
		return promocoes;
	}
	
	@Transactional
	public Produto save(Produto produto) {
		Produto _produto = produtoRepository.save(produto);
		return _produto;
	}
	
	@Transactional
	public Produto cadastrarProduto(long id) {
		Optional<Produto> _produto = produtoRepository.findById(id);
		
		if (_produto.isPresent()) {
			Produto produtoCadastrada = _produto.get();
			
			produtoCadastrada.setDataCadastro(LocalDateTime.now());
			produtoCadastrada.setStatusProduto("CADASTRADO");
			
			return produtoRepository.save(produtoCadastrada);
		}
		
		return null;
	}
	
	@Transactional
	public Produto inativar(long id) {
		Optional<Produto> _produto = produtoRepository.findById(id);
		
		if (_produto.isPresent()) {
			Produto produtoCadastrada = _produto.get();
			
			produtoCadastrada.setDataCadastro(LocalDateTime.now());
			produtoCadastrada.setStatusProduto("INATIVO");
		}
		
		return null;
	}
	
	@Transactional
	public Produto reativar(long id) {
		Optional<Produto> _produto = produtoRepository.findById(id);
		
		if (_produto.isPresent()) {
			Produto produtoCadastrada = _produto.get();
			
			produtoCadastrada.setDataCadastro(LocalDateTime.now());
			produtoCadastrada.setStatusProduto("ATIVO");
		}
		
		return null;
	}
	
}
