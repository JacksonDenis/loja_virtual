package jd.dev.repository;

import jd.dev.model.StatusRastreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio,Long> {
}
