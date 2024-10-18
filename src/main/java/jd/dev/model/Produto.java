package jd.dev.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", initialValue = 1, allocationSize = 1)
public class Produto implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "tipoUnidade do produto deve ser informado")
	private String tipoUnidade;
	
	@Column(nullable = false)
	@Size(min = 10, message = "O nome do produto tem que ter pelo menos 10 caracteres")
	@NotNull(message = "Nome do produto deve ser informado")
	private String nome;
	
	@Column(nullable = false)
	@NotNull(message = "ativo do produto deve ser informado")
	private Boolean ativo = Boolean.TRUE;
	
	@Column(columnDefinition = "text", nullable = false)
	@NotNull(message = "descricao do produto deve ser informado")
	private String descricao;
	 /*NOTA ITEM PRODUTO - ASSOCIAR*/
	@Column(nullable = false)
	@NotNull(message = "peso do produto deve ser informado")
	private Double peso;
	
	@Column(nullable = false)
	@NotNull(message = "Largura do produto deve ser informado")
	private Double largura;
	
	@Column(nullable = false)
	@NotNull(message = "altura do produto deve ser informado")
	private Double altura;
	
	@Column(nullable = false)
	@NotNull(message = "profundidade do produto deve ser informado")
	private Double profundidade;
	
	@Column(nullable = false)
	@NotNull(message = "valorDeVenda do produto deve ser informado")
	private BigDecimal valorDeVenda = BigDecimal.ZERO;
	
	@Column(nullable = false)
	@NotNull(message = "qtdEstoque do produto deve ser informado")
	private Integer qtdEstoque = 0;
	
	private Integer qtdAlertaEstoque=0;
	
	private String linkYoutube;
	
	private Boolean alertaQtdEstoque = Boolean.FALSE;
	
	private Integer qtdClique = 0;
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	@NotNull(message = "empresa do produto deve ser informado")
	private PessoaJuridica empresa;

	@ManyToOne(targetEntity = NotaItemProduto.class)
	@JoinColumn(name = "notaItemProduto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "notaItemProduto_fk"))
	@NotNull(message = "NotaItemProduto do produto deve ser informado")
	private NotaItemProduto notaItemProduto;

	@ManyToOne(targetEntity = CategoriaProduto.class)
	@JoinColumn(name = "categoriaProduto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoriaProduto_fk"))
	@NotNull(message = "categoriaProduto do produto deve ser informado")
	private CategoriaProduto categoriaProduto;

	@ManyToOne(targetEntity = MarcaProduto.class)
	@JoinColumn(name = "marcaProduto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "marcaProduto_fk"))
	@NotNull(message = "marcaProduto do produto deve ser informado")
	private MarcaProduto marcaProduto;

	public NotaItemProduto getNotaItemProduto() {
		return notaItemProduto;
	}

	public void setNotaItemProduto(NotaItemProduto notaItemProduto) {
		this.notaItemProduto = notaItemProduto;
	}

	public MarcaProduto getMarcaProduto() {
		return marcaProduto;
	}

	public void setMarcaProduto(MarcaProduto marcaProduto) {
		this.marcaProduto = marcaProduto;
	}

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}
	
	public PessoaJuridica getEmpresa() {
		return empresa;
	}
	
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoUnidade() {
		return tipoUnidade;
	}
	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public Double getLargura() {
		return largura;
	}
	public void setLargura(Double largura) {
		this.largura = largura;
	}
	public Double getAltura() {
		return altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}
	public Double getProfundidade() {
		return profundidade;
	}
	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}
	public BigDecimal getValorDeVenda() {
		return valorDeVenda;
	}
	public void setValorDeVenda(BigDecimal valorDeVenda) {
		this.valorDeVenda = valorDeVenda;
	}
	public Integer getQtdEstoque() {
		return qtdEstoque;
	}
	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}
	public Integer getQtdAlertaEstoque() {
		return qtdAlertaEstoque;
	}
	public void setQtdAlertaEstoque(Integer qtdAlertaEstoque) {
		this.qtdAlertaEstoque = qtdAlertaEstoque;
	}
	public String getLinkYoutube() {
		return linkYoutube;
	}
	public void setLinkYoutube(String linkYoutube) {
		this.linkYoutube = linkYoutube;
	}
	public Boolean getAlertaQtdEstoque() {
		return alertaQtdEstoque;
	}
	public void setAlertaQtdEstoque(Boolean alertaQtdEstoque) {
		this.alertaQtdEstoque = alertaQtdEstoque;
	}
	public Integer getQtdClique() {
		return qtdClique;
	}
	public void setQtdClique(Integer qtdClique) {
		this.qtdClique = qtdClique;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	

}
