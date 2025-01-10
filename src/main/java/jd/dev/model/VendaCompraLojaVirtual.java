package jd.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "vd_cp_loja_virt")
@SequenceGenerator(name = "seq_vd_cp_loja_virt", sequenceName = "seq_vd_cp_loja_virt", initialValue = 1, allocationSize = 1)
public class VendaCompraLojaVirtual implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vd_cp_loja_virt")
	private Long id;
	@NotNull(message = "A pessoa conpradora deve ser informada")
	@ManyToOne(targetEntity = PessoaFisica.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private PessoaFisica pessoa;
	@NotNull(message = "O endereço deve ser informado")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_entrega_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_entrega_fk"))
	private Endereco enderecoEntrega;
	@NotNull(message = "O endereço de cobrança deve ser informado")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_cobranca_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_cobranca_fk"))
	private Endereco endereCobranca;

	@Min(value = 1, message = "Valor total da venda é invalida")
	@Column(nullable = false)
	private BigDecimal valorTotal;

	private BigDecimal valorDesconto;

	@NotNull(message = "a forma de pagamento deve ser informado")
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "forma_pagamento_fk"))
	private FormaPagamento formaPagamento;
	@JsonIgnoreProperties(allowGetters = true)
	@NotNull(message = "A nota fiscal deve ser informado")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "nota_venda_id", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_venda_fk"))
	private NotaFiscalVenda notaFiscalVenda;
	
	
	@ManyToOne
	@JoinColumn(name = "cupom_desc_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cupom_desc_fk"))
	private CupomDesconto cupomDesconto;
	@NotNull(message = "a empresa deve ser informado")
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;

	@Column(nullable = false)
	private BigDecimal valorFrete;

	@Min(value = 1, message = "Dia de entrega é obrigatorio")
	@Column(nullable = false)
	private Integer diaEntrega;

	@NotNull(message = "a data da venda deve ser informado")
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataVenda;
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;

	@OneToMany(mappedBy = "vendaCompraLojaVirtual", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ItemVendaLoja> itemVendaLojas = new ArrayList<ItemVendaLoja>();
	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<ItemVendaLoja> getItemVendaLojas() {
		return itemVendaLojas;
	}
	public void setItemVendaLojas(List<ItemVendaLoja> itemVendaLojas) {
		this.itemVendaLojas = itemVendaLojas;
	}
	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PessoaFisica getPessoa() {
		return pessoa;
	}
	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}
	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	public Endereco getEndereCobranca() {
		return endereCobranca;
	}
	public void setEndereCobranca(Endereco endereCobranca) {
		this.endereCobranca = endereCobranca;
	}
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public NotaFiscalVenda getNotaFiscalVenda() {
		return notaFiscalVenda;
	}
	public void setNotaFiscalVenda(NotaFiscalVenda notaFiscalVenda) {
		this.notaFiscalVenda = notaFiscalVenda;
	}
	public CupomDesconto getCupomDesconto() {
		return cupomDesconto;
	}
	public void setCupomDesconto(CupomDesconto cupomDesconto) {
		this.cupomDesconto = cupomDesconto;
	}
	public BigDecimal getValorFrete() {
		return valorFrete;
	}
	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}
	public Integer getDiaEntrega() {
		return diaEntrega;
	}
	public void setDiaEntrega(Integer diaEntrega) {
		this.diaEntrega = diaEntrega;
	}
	public Date getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
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
		VendaCompraLojaVirtual other = (VendaCompraLojaVirtual) obj;
		return Objects.equals(id, other.id);
	}

	
	
	
}
