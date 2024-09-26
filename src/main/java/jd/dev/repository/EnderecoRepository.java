package jd.dev.repository;

import org.springframework.stereotype.Repository;

import jd.dev.model.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	

}
