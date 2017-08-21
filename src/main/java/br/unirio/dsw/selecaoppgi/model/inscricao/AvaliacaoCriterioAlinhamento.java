package br.unirio.dsw.selecaoppgi.model.inscricao;

import java.util.ArrayList;
import java.util.List;

import br.unirio.dsw.selecaoppgi.model.edital.CriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.edital.ProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.model.edital.SubcriterioAlinhamento;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa uma avaliação de um critério de alinhamento por um projeto de pesquisa
 * 
 * @author Marcio Barros
 */
public class AvaliacaoCriterioAlinhamento
{
	private @Getter CriterioAlinhamento criterioAlinhamento;
	private @Getter @Setter Boolean presenteProvaOral;
	private @Getter @Setter String justificativaNotasOriginal;
	private @Getter @Setter String justificativaNotasRecurso;
	private List<AvaliacaoSubcriterioAlinhamento> subcriterios;
	
	/**
	 * Inicializa a avaliação do critério de alinhamento
	 */
	public AvaliacaoCriterioAlinhamento(ProjetoPesquisa projeto, CriterioAlinhamento criterio)
	{
		this.criterioAlinhamento = criterio;
		this.presenteProvaOral = null;
		this.justificativaNotasOriginal = "";
		this.justificativaNotasRecurso = "";
		preparaAvaliacaoSubcriterios(criterio);
	}
	
	/**
	 * Cria uma avaliação para cada subcritério como parte do construtor
	 */
	private void preparaAvaliacaoSubcriterios(CriterioAlinhamento criterio)
	{
		this.subcriterios = new ArrayList<AvaliacaoSubcriterioAlinhamento>();
		
		for (SubcriterioAlinhamento subcriterio : criterio.getSubcriterios())
			this.subcriterios.add(new AvaliacaoSubcriterioAlinhamento(subcriterio));
	}
}