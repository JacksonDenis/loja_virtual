package jd.dev.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jd.dev.model.PessoaJuridica;

@Repository
public interface PessoaRepository extends CrudRepository<PessoaJuridica	, Long>{
	
	@Query(value = "select pj from PessoaJuridica pj where upper(trim(pj.nome)) like %?1%")
	public List<PessoaJuridica> pesquisaPorNomePJ(String nome);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	public PessoaJuridica existeCNPJCadastrado(String cnpj);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	public List<PessoaJuridica> existeCNPJCadastradoList(String cnpj);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.inscEstadual = ?1")
	public Object existeInscEstadualadastrado(String inscEstadual);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.inscEstadual = ?1")
	public List<Object> existeInscEstadualadastradoList(String inscEstadual);	
	
}
