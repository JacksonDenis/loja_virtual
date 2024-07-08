package jd.dev.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jd.dev.enums.StatusContaReceber;

@Entity
@Table(name = "contas_receber")
@SequenceGenerator(name = "seq_contas_receber", sequenceName = "seq_contas_receber", allocationSize = 1, initialValue = 1)
public class ContasReceber implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contas_receber")
	private Long id;
	
	@Column(nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusContaReceber status;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtaVencimento;
	
	
	@Temporal(TemporalType.DATE)
	private Date dtaPagamento;
	
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	private BigDecimal valorDesconto;
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Pessoa empresa;
	
	public void setEmpresa(Pessoa empresa) {
		this.empresa = empresa;
	}
	
	public Pessoa getEmpresa() {
		return empresa;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public StatusContaReceber getStatus() {
		return status;
	}



	public void setStatus(StatusContaReceber status) {
		this.status = status;
	}



	public Date getDtaVencimento() {
		return dtaVencimento;
	}



	public void setDtaVencimento(Date dtaVencimento) {
		this.dtaVencimento = dtaVencimento;
	}



	public Date getDtaPagamento() {
		return dtaPagamento;
	}



	public void setDtaPagamento(Date dtaPagamento) {
		this.dtaPagamento = dtaPagamento;
	}



	public BigDecimal getValorTotal() {
		return valorTotal;
	}



	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}



	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}



	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}



	public Pessoa getPessoa() {
		return pessoa;
	}



	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
		ContasReceber other = (ContasReceber) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	
	

}
