package jd.dev.model.dto;

import jd.dev.model.Produto;

public class ItemvendaDTO {
    private Double quantidade;
    private Long produto;

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Long getProduto() {
        return produto;
    }

    public void setProduto(Long produto) {
        this.produto = produto;
    }
}
