package jd.dev.repository;

import jd.dev.model.ContasPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContasPagarRepository extends JpaRepository<ContasPagar, Long> {
    @Query("select a from ContasPagar a where upper(trim(a.descricao)) like %?1%")
    List<ContasPagar> buscarContaDesc(String desc);

    @Query("select a from ContasPagar a where a.pessoa.id = ?1")
    List<ContasPagar> buscarContaPessoa(Long id);

    @Query("select a from ContasPagar a where a.pessoa_fornecedor.id = ?1")
    List<ContasPagar> buscarContaFornecedor(Long id_fornecedor);

    @Query("select a from ContasPagar a where a.empresa.id = ?1")
    List<ContasPagar> buscarContaEmpresa(Long id_empresa);
}
