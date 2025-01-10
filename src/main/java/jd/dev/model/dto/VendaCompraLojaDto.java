package jd.dev.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendaCompraLojaDto {
    private Long id;
    private String pessoa;
    private Long enderecoEntrega;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private String notaFiscalVenda;

    private List<ItemvendaDTO> itemvendaLoja = new ArrayList<ItemvendaDTO>();

    public List<ItemvendaDTO> getItemvendaLoja() {
        return itemvendaLoja;
    }

    public void setItemvendaLoja(List<ItemvendaDTO> itemvendaLoja) {
        this.itemvendaLoja = itemvendaLoja;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    }

    public Long getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Long enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getNotaFiscalVenda() {
        return notaFiscalVenda;
    }

    public void setNotaFiscalVenda(String notaFiscalVenda) {
        this.notaFiscalVenda = notaFiscalVenda;
    }
}
