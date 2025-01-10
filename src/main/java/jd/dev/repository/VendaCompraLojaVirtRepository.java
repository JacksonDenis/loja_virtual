package jd.dev.repository;

import jd.dev.model.VendaCompraLojaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VendaCompraLojaVirtRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {

}
