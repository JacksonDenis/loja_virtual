package jd.dev.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jd.dev.model.PessoaFisica;

@Repository
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica	, Long>{
	
	
}
