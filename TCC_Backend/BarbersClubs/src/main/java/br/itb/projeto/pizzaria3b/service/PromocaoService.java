package br.itb.projeto.pizzaria3b.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.itb.projeto.pizzaria3b.model.entity.Promocao;
import br.itb.projeto.pizzaria3b.model.repository.PromocaoRepository;
import jakarta.transaction.Transactional;

@Service
public class PromocaoService {

	private PromocaoRepository promocaoRepository; 
	// Source => Generate Constructor using Fields...
	public PromocaoService(PromocaoRepository promocaoRepository) {
		super();
		this.promocaoRepository = promocaoRepository;
	}
	
	public Promocao findById(long id) {
		Optional <Promocao> promocao = promocaoRepository.findById(id);
		if(promocao.isPresent()) {
			return promocao.get();
		}
		return null;
	}
	
	public List<Promocao> findAll(){
		List<Promocao> promocoes = promocaoRepository.findAll();
		return promocoes;
	}
	
	@Transactional
	public Promocao save(Promocao promocao) {
		Promocao _promocao = promocaoRepository.save(promocao);
		return _promocao;
	}
	
	@Transactional
	public Promocao cadastrarPromocao(long id) {
		Optional<Promocao> _promocao = promocaoRepository.findById(id);
		
		if (_promocao.isPresent()) {
			Promocao promocaoCadastrada = _promocao.get();
			
			promocaoCadastrada.setDataCadastro(LocalDateTime.now());
			promocaoCadastrada.setStatusPromocao("CADASTRADO");
			
			return promocaoRepository.save(promocaoCadastrada);
		}
		
		return null;
	}
	
	@Transactional
	public Promocao inativar(long id) {
		Optional<Promocao> _promocao = promocaoRepository.findById(id);
		
		if (_promocao.isPresent()) {
			Promocao promocaoCadastrada = _promocao.get();
			
			promocaoCadastrada.setDataCadastro(LocalDateTime.now());
			promocaoCadastrada.setStatusPromocao("INATIVO");
		}
		
		return null;
	}
	
	@Transactional
	public Promocao reativar(long id) {
		Optional<Promocao> _promocao = promocaoRepository.findById(id);
		
		if (_promocao.isPresent()) {
			Promocao promocaoCadastrada = _promocao.get();
			
			promocaoCadastrada.setDataCadastro(LocalDateTime.now());
			promocaoCadastrada.setStatusPromocao("ATIVO");
		}
		
		return null;
	}
	
}
