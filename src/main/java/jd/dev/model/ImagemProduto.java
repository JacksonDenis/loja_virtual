package jd.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "imagem_produto")
@SequenceGenerator(name = "seq_imagem_produto", sequenceName = "seq_imagem_produto", allocationSize = 1, initialValue = 1)
public class ImagemProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_imagem_produto")
	private Long id;
	
	
	@Column(columnDefinition = "text", nullable = false)
	private String imagenOriginal;
	
	@Column(columnDefinition = "text", nullable = false)
	private String imagenMiniatura;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
	private Produto produto;

	@JsonIgnore
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;
	
	public void setEmpresa(PessoaJuridica empresa) {
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
		ImagemProduto other = (ImagemProduto) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
