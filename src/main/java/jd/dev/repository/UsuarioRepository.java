package jd.dev.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jd.dev.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	@Query(value = "select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);

	@Query(value = "select u from Usuario u where u.pessoa.id=?1 or u.login =?2")
	Usuario findUserByPessoa(Long id, String email);

	@Query(value = "SELECT CONSTRAINT_name from information_schema.constraint_column_usage WHERE TABLE_name='usuarios_acesso' AND COLUMN_name= 'acesso_id' and CONSTRAINT_name <> 'unique_acesso_user'" , nativeQuery = true )
	String consultaConstraintAcesso();
	
	@Query(nativeQuery = true, value = "insert into usuarios_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = 'ROLE_USER'))")
	@Modifying
	@Transactional
	void insereAcessoUsersPj(Long id);
	
	@Query(nativeQuery = true, value = "insert into usuarios_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = ?2 limit 1))")
	@Modifying
	@Transactional
	void insereAcessoUsersPj(Long id, String acesso);
	
	@Query("select u from Usuario u.dataAtualSenha <= currente_date - 90")
	List<Usuario> usuariosSenhaVencida();
	

}
