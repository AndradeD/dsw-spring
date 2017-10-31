package br.unirio.dsw.selecaoppgi.service.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoProjetoPesquisa;

/**
 * Classe que publica os dados das inscrições em projeto de pesquisa de uma inscrição em formato JSON
 * 
 * @author Marcio Barros
 */
public class JsonInscricaoProjetoPesquisaWriter
{
	/**
	 * Gera a representação JSON
	 */
	public JsonArray execute(InscricaoEdital inscricao)
	{
		JsonArray jsonResultado = new JsonArray();
		
		for (InscricaoProjetoPesquisa inscricaoProjeto : inscricao.getInscricoesProjetoPesquisa())
		{
			JsonObject jsonProjeto = new JsonObject();
			jsonProjeto.addProperty("codigo", inscricaoProjeto.getProjetoPesquisa().getCodigo());
			jsonProjeto.addProperty("intencoes", inscricaoProjeto.getIntencoes());
			jsonResultado.add(jsonProjeto);
		}

		return jsonResultado;
	}
}