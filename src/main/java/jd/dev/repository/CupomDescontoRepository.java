package jd.dev.repository;

import jd.dev.model.CupomDesconto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Long> {
    @Query(value = "select c from CupomDesconto c where c.empresa.id = ?1 ")
    List<CupomDesconto> cupDescontoPorEmpresa(Long idEmpresa);
}
