package jd.dev.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jd.dev.model.PessoaFisica;

@Repository
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica	, Long>{
	
	@Query(value = "select pf from PessoaFisica pf where upper(trim(pf.nome)) like %?1%")
	public List<PessoaFisica> pesquisaPorNomePF(String nome);
	
	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public PessoaFisica existeCPFCadastrado(String cpf);
	
	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public List<PessoaFisica> existeCPFCadastradoList(String cpf);
	
}
