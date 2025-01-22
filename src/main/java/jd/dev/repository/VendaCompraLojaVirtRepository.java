package jd.dev.repository;

import jd.dev.model.VendaCompraLojaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface VendaCompraLojaVirtRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {
    @Query(value = "select a from VendaCompraLojaVirtual a where a.id = ?1 and a.excluido = false")
    VendaCompraLojaVirtual findByIdExclusao(Long id);

    @Query("SELECT i.vendaCompraLojaVirtual FROM ItemVendaLoja i " +
            "WHERE i.vendaCompraLojaVirtual.excluido = false AND i.produto.id = ?1")
    List<VendaCompraLojaVirtual> vendaPorProduto(Long idProduto);

}
