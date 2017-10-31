package br.unirio.dsw.selecaoppgi.model.inscricao;

import br.unirio.dsw.selecaoppgi.model.edital.SubcriterioAlinhamento;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa a avaliação de um subcritério de alinhamento em um projeto
 * 
 * @author Marcio Barros
 */
public class AvaliacaoSubcriterioAlinhamento
{
	private @Getter SubcriterioAlinhamento subcriterio;
	private @Getter @Setter Integer notaOriginal;
	private @Getter @Setter Integer notaRecurso;

	public AvaliacaoSubcriterioAlinhamento(SubcriterioAlinhamento subcriterio)
	{
		this.subcriterio = subcriterio;
		this.notaOriginal = null;
		this.notaRecurso = null;
	}
}