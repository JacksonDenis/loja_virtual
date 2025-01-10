package jd.dev.repository;

import jd.dev.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FormadePagamentoRepository extends JpaRepository<FormaPagamento, Long> {
}
