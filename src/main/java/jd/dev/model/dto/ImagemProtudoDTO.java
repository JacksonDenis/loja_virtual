package jd.dev.model.dto;

import java.io.Serializable;

public class ImagemProtudoDTO implements Serializable {
    private long id;
    private String imagenOriginal;
    private String imagenMiniatura;
    private Long produto;
    private Long empresa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagenOriginal() {
        return imagenOriginal;
    }

    public void setImagenOriginal(String imagenOriginal) {
        this.imagenOriginal = imagenOriginal;
    }

    public String getImagenMiniatura() {
        return imagenMiniatura;
    }

    public void setImagenMiniatura(String imagenMiniatura) {
        this.imagenMiniatura = imagenMiniatura;
    }

    public Long getProduto() {
        return produto;
    }

    public void setProduto(Long produto) {
        this.produto = produto;
    }

    public Long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Long empresa) {
        this.empresa = empresa;
    }
}
