package jd.dev.repository;

import jd.dev.model.NotaFiscalVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaFiscaldeVendaRepository extends JpaRepository<NotaFiscalVenda, Long> {
    @Query(value = "select n from NotaFiscalVenda n where n.vendaCompraLojaVirtual.id = ?1")
    NotaFiscalVenda buscaNotaPorvenda(Long idvenda);
}
