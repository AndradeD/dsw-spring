package br.unirio.dsw.selecaoppgi.model.inscricao;

import java.util.ArrayList;
import java.util.List;

import br.unirio.dsw.selecaoppgi.model.edital.CriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.ProjetoPesquisa;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa a inscrição de um candidato em um projeto de pesquisa
 * 
 * @author Marcio Barros
 */
public class InscricaoProjetoPesquisa
{
	private @Getter ProjetoPesquisa projetoPesquisa;
	private @Getter @Setter String intencoes;
	private List<AvaliacaoCriterioAlinhamento> criteriosAlinhamento;
	
	/**
	 * Inicializa a inscrição no projeto de pesquisa
	 */
	public InscricaoProjetoPesquisa(ProjetoPesquisa projeto, Edital edital)
	{
		this.projetoPesquisa = projeto;
		this.intencoes = "";
		this.criteriosAlinhamento = new ArrayList<AvaliacaoCriterioAlinhamento>();
		preparaAvaliacoesCriteriosAlinhamento(edital);
	}

	/**
	 * Cria as avaliações referentes aos critérios de alinhamento do projeto
	 */
	private void preparaAvaliacoesCriteriosAlinhamento(Edital edital)
	{
		for (CriterioAlinhamento criterio : edital.getCriteriosAlinhamento())
		{
			if (!criterio.isPertenceProvaOral() || projetoPesquisa.isExigeProvaOral())
			{
				criteriosAlinhamento.add(new AvaliacaoCriterioAlinhamento(projetoPesquisa, criterio));
			}
		}
	}
	
	/**
	 * Conta o número de avaliações de critério de alinhamento
	 */
	public int contaAvaliacoesCriteriosAlinhamento()
	{
		return criteriosAlinhamento.size();
	}
	
	/**
	 * Retorna uma avaliação de critério de alinhamento, dado seu índice
	 */
	public AvaliacaoCriterioAlinhamento pegaAvaliacaoCriterioAlinhamento(int indice)
	{
		return criteriosAlinhamento.get(indice);
	}

	/**
	 * Retorna uma avaliação de critério de alinhamento, dado o critério
	 */
	public AvaliacaoCriterioAlinhamento pegaAvaliacaoCriterioAlinhamento(CriterioAlinhamento criterio)
	{
		for (AvaliacaoCriterioAlinhamento avaliacao : criteriosAlinhamento)
			if (avaliacao.getCriterioAlinhamento() == criterio)
				return avaliacao;
		
		return null;
	}
	
	/**
	 * Retorna todas as avaliações de critérios de alinhamento
	 */
	public Iterable<AvaliacaoCriterioAlinhamento> getAvaliacoesCriterioAlinhamento()
	{
		return criteriosAlinhamento;
	}
}