package br.itb.projeto.pizzaria3b.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.itb.projeto.pizzaria3b.model.entity.Categoria;
import br.itb.projeto.pizzaria3b.model.repository.CategoriaRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {
	
	private CategoriaRepository categoriaRepository; 
	// Source => Generate Constructor using Fields... 
	public CategoriaService(CategoriaRepository categoriaRepository) {
		super();
		this.categoriaRepository = categoriaRepository;
	}
	
	public Categoria findById(long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		// OLD: orElseThrow() => ouEntãoJogue - de colocar, jogar isso
		if(categoria.isPresent()) {
			return categoria.get();
		}
		return null;
	}
	
	public List<Categoria> findAll(){
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias;
	}
	
	@Transactional
	public Categoria save(Categoria categoria) {
		Categoria _categoria = categoriaRepository.save(categoria);
		return _categoria;
	}
	
	@Transactional
	public Categoria cadastrarCategoria(long id) {
		Optional<Categoria> _categoria = categoriaRepository.findById(id);
		
		if (_categoria.isPresent()) {
			Categoria categoriaCadastrada = _categoria.get();
			
			categoriaCadastrada.setDataCadastro(LocalDateTime.now());
			categoriaCadastrada.setStatusCategoria("CADASTRADO");
			
			return categoriaRepository.save(categoriaCadastrada);
		}
		
		return null;
	}
	
	@Transactional
	public Categoria inativar(long id) {
		Optional<Categoria> _categoria = categoriaRepository.findById(id);
		
		if (_categoria.isPresent()) {
			Categoria categoriaCadastrada = _categoria.get();
			
			categoriaCadastrada.setDataCadastro(LocalDateTime.now());
			categoriaCadastrada.setStatusCategoria("INATIVO");
			
			return categoriaRepository.save(categoriaCadastrada);
		}
		
		return null;
	}
	
	@Transactional
	public Categoria reativar(long id) {
		Optional<Categoria> _categoria = categoriaRepository.findById(id);
		
		if (_categoria.isPresent()) {
			Categoria categoriaCadastrada = _categoria.get();
			
			categoriaCadastrada.setDataCadastro(LocalDateTime.now());
			categoriaCadastrada.setStatusCategoria("ATIVO");
			
			return categoriaRepository.save(categoriaCadastrada);
		}
		
		return null;
	}
	
}















