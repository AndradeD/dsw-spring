package br.unirio.dsw.selecaoppgi.view.edital;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Classe que representa o formulário para edição de provas
 * 
 * @author Marcio
 */
public @Data class ProvaEscritaForm 
{
	private int idEdital = -1;
	private String codigoOriginal = "";
	private String codigo = "";
	private String nome = "";
	private int notaMinimaAprovacao = 70;
	private boolean dispensavel = false;
	private List<Integer> pesosQuestoes;
	
	/**
	 * Inicializa o formulário de provas escritas
	 */
	public ProvaEscritaForm()
	{
		this.pesosQuestoes = new ArrayList<Integer>();
	}

	/**
	 * Adiciona um conjunto de pesos de questões
	 */
	public void adicionaPesosQuestoes(Iterable<Integer> pesosQuestoes) 
	{
		for (Integer peso : pesosQuestoes)
			this.pesosQuestoes.add(peso);
	}

	/**
	 * Adiciona um peso de questão
	 */
	public void adicionaPesoQuestao(int peso) 
	{
		this.pesosQuestoes.add(peso);
	}
}