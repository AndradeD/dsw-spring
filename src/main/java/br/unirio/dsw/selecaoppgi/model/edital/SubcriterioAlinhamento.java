package br.unirio.dsw.selecaoppgi.model.edital;

import lombok.Data;

/**
 * Classe que representa um subcriterio da prova de alinhamento de pesquisa
 * 
 * @author marciobarros
 */
public @Data class SubcriterioAlinhamento
{
	private String codigo;
	private String nome;
	private String descricao;
	private int peso;
	
	/**
	 * Inicializa o subcritério
	 */
	public SubcriterioAlinhamento()
	{
		this.codigo = "";
		this.nome = "";
		this.descricao = "";
		this.peso = 0;
	}
}