package br.unirio.dsw.selecaoppgi.model.edital;

import lombok.Getter;

/**
 * Enumeração dos possíveis status de um edital
 * 
 * @author marciobarros
 */
public enum StatusEdital
{
	Preparacao(0, "Em preparação"),
	Aberto(1, "Aberto"),
	Homologacao(2, "Em homologação"),
	ProvaEscrita(3, "Em provas escritas"),
	ProvaAlinhamento(4, "Em provas de alinhamento"),
	Finalizado(5, "Finalizado");
	
	private @Getter int codigo;
	private @Getter String nome;
	
	/**
	 * Inicializa o status de edital
	 */
	private StatusEdital(int codigo, String nome)
	{
		this.codigo = codigo;
		this.nome = nome;
	}
	
	/**
	 * Retorna um status, dado seu código numérico
	 */
	public static StatusEdital get(int codigo)
	{
		for (StatusEdital status : values())
			if (status.getCodigo() == codigo)
				return status;
		
		return null;
	}
}