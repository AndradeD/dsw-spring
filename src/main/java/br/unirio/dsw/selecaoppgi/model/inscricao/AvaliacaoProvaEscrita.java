package br.unirio.dsw.selecaoppgi.model.inscricao;

import br.unirio.dsw.selecaoppgi.model.edital.ProvaEscrita;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa uma inscrição em prova escrita
 * 
 * @author Marcio Barros
 */
public class AvaliacaoProvaEscrita
{
	private @Getter ProvaEscrita provaEscrita;
	private @Getter @Setter Boolean presente;
	private Integer[] notasOriginalQuestao;
	private Integer[] notasRecursoQuestao;
	
	/**
	 * Inicializa uma inscrição em prova escrita
	 */
	public AvaliacaoProvaEscrita(ProvaEscrita provaEscrita)
	{
		this.provaEscrita = provaEscrita;
		this.presente = null;
		this.notasOriginalQuestao = new Integer[provaEscrita.contaQuestoes()];
		this.notasRecursoQuestao = new Integer[provaEscrita.contaQuestoes()];
	}
	
	/**
	 * Verifica se tem uma nota da avaliação original para uma questão da prova
	 */
	public boolean possuiNotaOriginalQuestao(int indiceQuestao)
	{
		return notasOriginalQuestao[indiceQuestao] != null;
	}
	
	/**
	 * Retorna uma nota da avaliação original para uma questão da prova
	 */
	public int getNotaOriginalQuestao(int indiceQuestao)
	{
		return notasOriginalQuestao[indiceQuestao];
	}
	
	/**
	 * Altera uma nota da avaliação original para uma questão da prova
	 */
	public void setNotaOriginalQuestao(int indiceQuestao, int nota)
	{
		notasOriginalQuestao[indiceQuestao] = nota;
	}
	
	/**
	 * Verifica se tem uma nota da avaliação de recurso para uma questão da prova
	 */
	public boolean possuiNotaRecursoQuestao(int indiceQuestao)
	{
		return notasRecursoQuestao[indiceQuestao] != null;
	}
	
	/**
	 * Retorna uma nota de recurso para uma questão da prova
	 */
	public int getNotaRecursoQuestao(int indiceQuestao)
	{
		return notasRecursoQuestao[indiceQuestao];
	}
	
	/**
	 * Altera uma nota de recurso para uma questão da prova
	 */
	public void setNotaRecursoQuestao(int indiceQuestao, int nota)
	{
		notasRecursoQuestao[indiceQuestao] = nota;
	}
}