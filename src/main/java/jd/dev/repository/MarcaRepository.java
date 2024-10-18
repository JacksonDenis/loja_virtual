package jd.dev.repository;

import jd.dev.model.Acesso;
import jd.dev.model.MarcaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaRepository extends JpaRepository<MarcaProduto, Long> {
    @Query("select a from MarcaProduto a where upper(trim(a.nomeDesc)) like %?1%")
    List<MarcaProduto> buscarMarcaProduto(String desc);
}
