package jd.dev.repository;

import jd.dev.model.CategoriaProduto;
import jd.dev.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    @Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(nome) = upper(?1)")
    public boolean existeProduto(String nomeProduto) ;

    @Query("select a from Produto a where upper(trim(a.nome)) like %?1%")
    public List<Produto> buscarProdutoNome(String nome) ;


}
