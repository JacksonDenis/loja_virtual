package jd.dev.enums;

public enum StatusContaPagar {
	COBRANCA("Pagar"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	NEGOCIADA("Renegociada"),
	QUITADA("Quitada");
	
	private String descricao;
	
	private StatusContaPagar(String descricao) {
		this.descricao=descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.toString();
	}

}
