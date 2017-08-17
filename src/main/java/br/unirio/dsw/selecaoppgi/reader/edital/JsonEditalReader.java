package br.unirio.dsw.selecaoppgi.reader.edital;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.CriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.ProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.model.edital.ProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.edital.StatusEdital;
import br.unirio.dsw.selecaoppgi.model.edital.SubcriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.usuario.User;
import br.unirio.dsw.selecaoppgi.service.dao.UserDAO;

/**
 * Classe responsável por carregar um edital a partir da sua representação JSON
 * 
 * @author marciobarros
 */
public class JsonEditalReader
{
	/**
	 * Carrega um edital a partir da representação JSON
	 */
	public void execute(JsonObject json, Edital edital, UserDAO userDAO)
	{
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

		int id = json.get("id").getAsInt();
		edital.setId(id);

		String nome = json.get("nome").getAsString();
		edital.setNome(nome);

		StatusEdital status = StatusEdital.get(json.get("status").getAsInt());
		edital.setStatus(status);

		String sDataInicio = json.get("dataInicio").getAsString();
		edital.setDataInicio(fmt.parseDateTime(sDataInicio));

		String sDataTermino = json.get("dataTermino").getAsString();
		edital.setDataTermino(fmt.parseDateTime(sDataTermino));
		
		int notaMinima = json.get("notaMinimaAlinhamento").getAsInt();
		edital.setNotaMinimaAlinhamento(notaMinima);

		carregaRepresentacaoComissaoSelecao(json, edital, userDAO);
		carregaRepresentacaoComissaoRecursos(json, edital, userDAO);
		carregaRepresentacaoProvasEscritas(json, edital);
		carregaRepresentacaoProjetosPesquisa(json, edital, userDAO);
		carregaRepresentacaoCriteriosAlinhamento(json, edital);
	}

	/**
	 * Carrega a comissão de seleção a partir da representação JSON
	 */
	private void carregaRepresentacaoComissaoSelecao(JsonObject json, Edital edital, UserDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("selecao");
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			User professor = userDAO.getUserId(id);
			
			if (professor != null)
				edital.adicionaComissaoSelecao(professor);
		}
	}

	/**
	 * Carrega a comissão de recursos a partir da representação JSON
	 */
	private void carregaRepresentacaoComissaoRecursos(JsonObject json, Edital edital, UserDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("recursos");
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			User professor = userDAO.getUserId(id);
			
			if (professor != null)
				edital.adicionaComissaoRecurso(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritas(JsonObject json, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			JsonObject jsonProva = jsonProvas.get(i).getAsJsonObject();
			ProvaEscrita prova = carregaRepresentacaoProvaEscrita(jsonProva);
			edital.adicionaProvasEscrita(prova);
		}
	}

	/**
	 * Carrega uma prova escrita a partir da representação JSON
	 */
	private ProvaEscrita carregaRepresentacaoProvaEscrita(JsonObject json)
	{
		ProvaEscrita prova = new ProvaEscrita();
		prova.setSigla(json.get("sigla").getAsString());
		prova.setNome(json.get("nome").getAsString());
		prova.setDispensavel(json.get("dispensavel").getAsBoolean());
		prova.setNotaMinimaAprovacao(json.get("notaMinima").getAsInt());
		
		JsonArray jsonQuestoes = json.getAsJsonArray("questoes");
		
		for (int i = 0; i < jsonQuestoes.size(); i++)
		{
			int peso = jsonQuestoes.get(i).getAsInt();
			prova.adicionaQuestao(peso);
		}
		
		return prova;
	}

	/**
	 * Carrega a lista de projetos de pesquisa a partir da representação JSON
	 */
	private void carregaRepresentacaoProjetosPesquisa(JsonObject json, Edital edital, UserDAO userDAO)
	{
		JsonArray jsonProjetos = json.getAsJsonArray("projetos");
		
		for (int i = 0; i < jsonProjetos.size(); i++)
		{
			JsonObject jsonProjeto = jsonProjetos.get(i).getAsJsonObject();
			ProjetoPesquisa projeto = carregaRepresentacaoProjetoPesquisa(jsonProjeto, edital, userDAO);
			edital.adicionaProjetoPesquisa(projeto);
		}
	}

	/**
	 * Carrega um projeto de pesquisa a partir da representação JSON
	 */
	private ProjetoPesquisa carregaRepresentacaoProjetoPesquisa(JsonObject json, Edital edital, UserDAO userDAO)
	{
		ProjetoPesquisa projeto = new ProjetoPesquisa();
		projeto.setCodigo(json.get("codigo").getAsString());
		projeto.setNome(json.get("nome").getAsString());
		projeto.setExigeProvaOral(json.get("exigeProvaOral").getAsBoolean());
		carregaRepresentacaoProfessoresProjetoPesquisa(json, projeto, userDAO);
		carregaRepresentacaoProvasEscritasProjetoPesquisa(json, projeto, edital);
		return projeto;
	}

	/**
	 * Carrega a lista de professores de um projeto de pesquisa a partir da representação JSON
	 */
	private void carregaRepresentacaoProfessoresProjetoPesquisa(JsonObject json, ProjetoPesquisa projeto, UserDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("professores");
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			User professor = userDAO.getUserId(id);
			
			if (professor != null)
				projeto.adicionaProfessor(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritasProjetoPesquisa(JsonObject json, ProjetoPesquisa projeto, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			String sigla = jsonProvas.get(i).getAsString();
			ProvaEscrita prova = edital.pegaProvaEscritaSigla(sigla);
			
			if (prova != null)
				projeto.adicionaProvaEscrita(prova);
		}
	}

	/**
	 * Carrega a lista de critérios de alinhamento a partir da representação JSON
	 */
	private void carregaRepresentacaoCriteriosAlinhamento(JsonObject json, Edital edital)
	{
		JsonArray jsonCriterios = json.getAsJsonArray("criterios");
		
		for (int i = 0; i < jsonCriterios.size(); i++)
		{
			JsonObject jsonCriterio = jsonCriterios.get(i).getAsJsonObject();
			CriterioAlinhamento criterio = carregaRepresentacaoCriterioAlinhamento(jsonCriterio);
			edital.adicionaCriterioAlinhamento(criterio);
		}
	}

	/**
	 * Carrega um criterio de alinhamento de pesquisa a partir da representação JSON
	 */
	private CriterioAlinhamento carregaRepresentacaoCriterioAlinhamento(JsonObject json)
	{
		CriterioAlinhamento criterio = new CriterioAlinhamento();
		criterio.setNome(json.get("nome").getAsString());
		criterio.setPesoComProvaOral(json.get("pesoComProvaOral").getAsInt());
		criterio.setPesoSemProvaOral(json.get("pesoSemProvaOral").getAsInt());
		criterio.setPertenceProvaOral(json.get("pertenceProvaOral").getAsBoolean());
		
		JsonArray jsonSubcriterios = json.getAsJsonArray("subcriterios");
		
		for (int i = 0; i < jsonSubcriterios.size(); i++)
		{
			JsonObject jsonSubcriterio = jsonSubcriterios.get(i).getAsJsonObject();
			SubcriterioAlinhamento subcriterio = carregaRepresentacaoSubcriterioAlinhamento(jsonSubcriterio); 
			criterio.adicionaSubcriterio(subcriterio);
		}
		
		return criterio;
	}

	/**
	 * Carrega um subcriterio de alinhamento de pesquisa a partir da representação JSON
	 */
	private SubcriterioAlinhamento carregaRepresentacaoSubcriterioAlinhamento(JsonObject json)
	{
		SubcriterioAlinhamento subcriterio = new SubcriterioAlinhamento();
		subcriterio.setNome(json.get("nome").getAsString());
		subcriterio.setDescricao(json.get("descricao").getAsString());
		subcriterio.setPeso(json.get("peso").getAsInt());
		return subcriterio;
	}
}