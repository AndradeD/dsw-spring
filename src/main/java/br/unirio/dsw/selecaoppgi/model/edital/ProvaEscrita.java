package br.unirio.dsw.selecaoppgi.model.edital;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa uma prova escrita
 * 
 * @author marciobarros
 */
public class ProvaEscrita
{
	private @Getter @Setter String codigo;
	private @Getter @Setter String nome;
	private @Getter @Setter boolean dispensavel;
	private @Getter @Setter int notaMinimaAprovacao;
	private List<Integer> pesosQuestoes;

	/**
	 * Inicializa uma prova escrita
	 */
	public ProvaEscrita()
	{
		this.codigo = "";
		this.nome = "";
		this.dispensavel = false;
		this.notaMinimaAprovacao = 0;
		this.pesosQuestoes = new ArrayList<Integer>();
	}

	/**
	 * Conta o número de questões da prova
	 */
	public int contaQuestoes()
	{
		return pesosQuestoes.size();
	}

	/**
	 * Retorna o peso de uma questão, dado seu índice
	 */
	public int pegaPesoQuestaoIndice(int indice)
	{
		return pesosQuestoes.get(indice);
	}

	/**
	 * Retorna os pesos das questões da prova escrita
	 */
	public Iterable<Integer> getPesosQuestoes()
	{
		return pesosQuestoes;
	}

	/**
	 * Adiciona uma questão na prova, dado seu peso
	 */
	public void adicionaQuestao(int peso)
	{
		pesosQuestoes.add(peso);
	}

	/**
	 * Remove uma questão, dado seu índice
	 */
	public void removeQuestao(int indice)
	{
		pesosQuestoes.remove(indice);
	}

	/**
	 * Remove todas as questões
	 */
	public void limpaQuestoes()
	{
		pesosQuestoes.clear();
	}

	/**
	 * Retorna os pesos de uma prova formatados
	 */
	public String getPesosQuestoesFormatados() 
	{
		int len = pesosQuestoes.size();
		
		if (len == 0)
			return ""; 
		
		String result = ""+ pesosQuestoes.get(0);
		
		for (int i = 1; i < len; i++)
			result += ", " + pesosQuestoes.get(i);
		
		return result;
	}
}