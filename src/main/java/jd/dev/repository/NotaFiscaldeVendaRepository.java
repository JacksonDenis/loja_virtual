package jd.dev.repository;

import jd.dev.model.NotaFiscalVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaFiscaldeVendaRepository extends JpaRepository<NotaFiscalVenda, Long> {
}
