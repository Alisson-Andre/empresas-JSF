package br.com.empresas.model;

public enum TipoEmpresa {

	MEI("Microempreededor Individual"),
	EIRELI("Empresa Individual de Resposabilidade Limitada"),
	LTDA("Sociedade Limitada"),
	SA("Sociedade Anônima");
	
	private String descricao;
	
	TipoEmpresa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
