package jd.dev.service;

import jd.dev.model.VendaCompraLojaVirtual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class VendaService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;




    public void exclusaoTotalVendaSemApagar(Long idVenda) {
        String sql = "BEGIN;"
                + "UPDATE vd_cp_loja_virt set excluido = true where id = "+idVenda+";"
                + "COMMIT ;";

        jdbcTemplate.execute(sql);
    }

    public void ativarTotalVendaSemApagar(Long idVenda) {
        String sql = "BEGIN;"
                + "UPDATE vd_cp_loja_virt set excluido = false where id = "+idVenda+";"
                + "COMMIT ;";

        jdbcTemplate.execute(sql);
    }

    public void exclusaoTotalVendaBanco(Long idVenda) {
        String value = "BEGIN;"
                + "UPDATE nota_fiscal_venda set vd_cp_lg_virt=null where vd_cp_lg_virt = "+idVenda+";"
                + "DELETE from nota_fiscal_venda where vd_cp_lg_virt = "+idVenda+";"
                + "DELETE from item_venda_loja where vd_cp_loja_virtual_id = "+idVenda+";"
                + "DELETE from status_rastreio where venda_compra_loja_id = "+idVenda+";"
                + "DELETE from vd_cp_loja_virt where id = "+idVenda+";"
                + "COMMIT ;";

        jdbcTemplate.execute(value);
    }

    @SuppressWarnings("unchecked")
    public List<VendaCompraLojaVirtual> consultaVendaFaixaData(String data1, String data2){

        String sql = "select distinct(i.vendaCompraLojaVirtual) from ItemVendaLoja i "
                + " where i.vendaCompraLojaVirtual.excluido = false "
                + " and i.vendaCompraLojaVirtual.dataVenda >= '" + data1 + "'"
                + " and i.vendaCompraLojaVirtual.dataVenda <= '" + data2 + "'";

        return entityManager.createQuery(sql).getResultList();

    }

}
