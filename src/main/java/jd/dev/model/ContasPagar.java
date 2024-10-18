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
import javax.validation.constraints.NotNull;

import jd.dev.enums.StatusContaPagar;


@Entity
@Table(name = "contas_pagar")
@SequenceGenerator(name = "seq_contas_pagar", sequenceName = "seq_contas_pagar", allocationSize = 1, initialValue = 1)
public class ContasPagar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contas_pagar")
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "descricao da conta deve ser informado")
	private String descricao;

	@Column(nullable = false)
	@NotNull(message = "status da conta deve ser informado")
	@Enumerated(EnumType.STRING)
	private StatusContaPagar status;

	@Column(nullable = false)
	@NotNull(message = "dtaVencimento deve ser informado")
	@Temporal(TemporalType.DATE)
	private Date dtaVencimento;

	@Temporal(TemporalType.DATE)
	private Date dtaPagamento;

	@Column(nullable = false)
	@NotNull(message = "valorTotal deve ser informado")
	private BigDecimal valorTotal;

	private BigDecimal valorDesconto;

	@ManyToOne(targetEntity = Pessoa.class)
	@NotNull(message = "pessoa deve ser informado")
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private PessoaFisica pessoa;

	@ManyToOne(targetEntity = Pessoa.class)
	@NotNull(message = "pessoa_fornecedor deve ser informado")
	@JoinColumn(name = "pessoa_forn_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_forn_fk"))
	private PessoaJuridica pessoa_fornecedor;

	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	@NotNull(message = "empresa do produto deve ser informado")
	private PessoaJuridica empresa;

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public PessoaJuridica getEmpresa() {
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

	public StatusContaPagar getStatus() {
		return status;
	}

	public void setStatus(StatusContaPagar status) {
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

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public PessoaJuridica getPessoa_fornecedor() {
		return pessoa_fornecedor;
	}

	public void setPessoa_fornecedor(PessoaJuridica pessoa_fornecedor) {
		this.pessoa_fornecedor = pessoa_fornecedor;
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
		ContasPagar other = (ContasPagar) obj;
		return Objects.equals(id, other.id);
	}


}
