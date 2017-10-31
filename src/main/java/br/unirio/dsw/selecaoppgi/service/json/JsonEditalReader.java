<<<<<<< HEAD
package br.unirio.dsw.selecaoppgi.service.json;

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
import br.unirio.dsw.selecaoppgi.model.usuario.Usuario;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;

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
	public Edital execute(JsonObject json, UsuarioDAO userDAO)
	{
		Edital edital = new Edital();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

		int id = json.get("id").getAsInt();
		edital.setId(id);

		String nome = json.get("nome").getAsString();
		edital.setNome(nome);

		StatusEdital status = StatusEdital.get(json.get("codigoStatus").getAsInt());
		edital.setStatus(status);

		if (json.has("dataInicio"))
		{
			String sDataInicio = json.get("dataInicio").getAsString();
			edital.setDataInicio(fmt.parseDateTime(sDataInicio));
		}
		
		if (json.has("dataTermino"))
		{
			String sDataTermino = json.get("dataTermino").getAsString();
			edital.setDataTermino(fmt.parseDateTime(sDataTermino));
		}
		
		int notaMinima = json.get("notaMinimaAlinhamento").getAsInt();
		edital.setNotaMinimaAlinhamento(notaMinima);

		carregaRepresentacaoComissaoSelecao(json, edital, userDAO);
		carregaRepresentacaoComissaoRecursos(json, edital, userDAO);
		carregaRepresentacaoProvasEscritas(json, edital);
		carregaRepresentacaoProjetosPesquisa(json, edital, userDAO);
		carregaRepresentacaoCriteriosAlinhamento(json, edital);
		return edital;
	}

	/**
	 * Carrega a comissão de seleção a partir da representação JSON
	 */
	private void carregaRepresentacaoComissaoSelecao(JsonObject json, Edital edital, UsuarioDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("comissaoSelecao");
		
		if (jsonProfessores == null)
			return;
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			Usuario professor = userDAO.carregaUsuarioId(id);
			
			if (professor != null)
				edital.adicionaComissaoSelecao(professor);
		}
	}

	/**
	 * Carrega a comissão de recursos a partir da representação JSON
	 */
	private void carregaRepresentacaoComissaoRecursos(JsonObject json, Edital edital, UsuarioDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("comissaoRecurso");
		
		if (jsonProfessores == null)
			return;
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			Usuario professor = userDAO.carregaUsuarioId(id);
			
			if (professor != null)
				edital.adicionaComissaoRecurso(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritas(JsonObject json, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provasEscritas");
		
		if (jsonProvas == null)
			return;
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			JsonObject jsonProva = jsonProvas.get(i).getAsJsonObject();
			ProvaEscrita prova = carregaRepresentacaoProvaEscrita(jsonProva);
			edital.adicionaProvaEscrita(prova);
		}
	}

	/**
	 * Carrega uma prova escrita a partir da representação JSON
	 */
	private ProvaEscrita carregaRepresentacaoProvaEscrita(JsonObject json)
	{
		ProvaEscrita prova = new ProvaEscrita();
		prova.setCodigo(json.get("codigo").getAsString());
		prova.setNome(json.get("nome").getAsString());
		
		if (json.has("dispensavel"))
			prova.setDispensavel(json.get("dispensavel").getAsBoolean());
		
		prova.setNotaMinimaAprovacao(json.get("notaMinimaAprovacao").getAsInt());
		
		JsonArray jsonQuestoes = json.getAsJsonArray("pesosQuestoes");
		
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
	private void carregaRepresentacaoProjetosPesquisa(JsonObject json, Edital edital, UsuarioDAO userDAO)
	{
		JsonArray jsonProjetos = json.getAsJsonArray("projetosPesquisa");
		
		if (jsonProjetos == null)
			return;
		
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
	private ProjetoPesquisa carregaRepresentacaoProjetoPesquisa(JsonObject json, Edital edital, UsuarioDAO userDAO)
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
	private void carregaRepresentacaoProfessoresProjetoPesquisa(JsonObject json, ProjetoPesquisa projeto, UsuarioDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("professores");
		
		if (jsonProfessores == null)
			return;
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			Usuario professor = userDAO.carregaUsuarioId(id);
			
			if (professor != null)
				projeto.adicionaProfessor(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritasProjetoPesquisa(JsonObject json, ProjetoPesquisa projeto, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provasEscritas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			String codigo = jsonProvas.get(i).getAsString();
			ProvaEscrita prova = edital.pegaProvaEscritaCodigo(codigo);
			
			if (prova != null)
				projeto.adicionaProvaEscrita(prova);
		}
	}

	/**
	 * Carrega a lista de critérios de alinhamento a partir da representação JSON
	 */
	private void carregaRepresentacaoCriteriosAlinhamento(JsonObject json, Edital edital)
	{
		JsonArray jsonCriterios = json.getAsJsonArray("criteriosAlinhamento");
		
		if (jsonCriterios == null)
			return;
		
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
		criterio.setCodigo(json.get("codigo").getAsString());
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
		subcriterio.setCodigo(json.get("codigo").getAsString());
		subcriterio.setNome(json.get("nome").getAsString());
		subcriterio.setDescricao(json.get("descricao").getAsString());
		subcriterio.setPeso(json.get("peso").getAsInt());
		return subcriterio;
	}
=======
package br.unirio.dsw.selecaoppgi.service.json;

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
import br.unirio.dsw.selecaoppgi.model.usuario.Usuario;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;

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
	public Edital execute(JsonObject json, UsuarioDAO userDAO)
	{
		Edital edital = new Edital();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

		int id = json.get("id").getAsInt();
		edital.setId(id);

		String nome = json.get("nome").getAsString();
		edital.setNome(nome);

		StatusEdital status = StatusEdital.get(json.get("codigoStatus").getAsInt());
		edital.setStatus(status);

		if (json.has("dataInicio"))
		{
			String sDataInicio = json.get("dataInicio").getAsString();
			edital.setDataInicio(fmt.parseDateTime(sDataInicio));
		}
		
		if (json.has("dataTermino"))
		{
			String sDataTermino = json.get("dataTermino").getAsString();
			edital.setDataTermino(fmt.parseDateTime(sDataTermino));
		}
		
		int notaMinima = json.get("notaMinimaAlinhamento").getAsInt();
		edital.setNotaMinimaAlinhamento(notaMinima);

		carregaRepresentacaoComissaoSelecao(json, edital, userDAO);
		carregaRepresentacaoComissaoRecursos(json, edital, userDAO);
		carregaRepresentacaoProvasEscritas(json, edital);
		carregaRepresentacaoProjetosPesquisa(json, edital, userDAO);
		carregaRepresentacaoCriteriosAlinhamento(json, edital);
		return edital;
	}

	/**
	 * Carrega a comissão de seleção a partir da representação JSON
	 */
	private void carregaRepresentacaoComissaoSelecao(JsonObject json, Edital edital, UsuarioDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("comissaoSelecao");
		
		if (jsonProfessores == null)
			return;
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			Usuario professor = userDAO.carregaUsuarioId(id);
			
			if (professor != null)
				edital.adicionaComissaoSelecao(professor);
		}
	}

	/**
	 * Carrega a comissão de recursos a partir da representação JSON
	 */
	private void carregaRepresentacaoComissaoRecursos(JsonObject json, Edital edital, UsuarioDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("comissaoRecurso");
		
		if (jsonProfessores == null)
			return;
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			Usuario professor = userDAO.carregaUsuarioId(id);
			
			if (professor != null)
				edital.adicionaComissaoRecurso(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritas(JsonObject json, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provasEscritas");
		
		if (jsonProvas == null)
			return;
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			JsonObject jsonProva = jsonProvas.get(i).getAsJsonObject();
			ProvaEscrita prova = carregaRepresentacaoProvaEscrita(jsonProva);
			edital.adicionaProvaEscrita(prova);
		}
	}

	/**
	 * Carrega uma prova escrita a partir da representação JSON
	 */
	private ProvaEscrita carregaRepresentacaoProvaEscrita(JsonObject json)
	{
		ProvaEscrita prova = new ProvaEscrita();
		prova.setCodigo(json.get("codigo").getAsString());
		prova.setNome(json.get("nome").getAsString());
		
		if (json.has("dispensavel"))
			prova.setDispensavel(json.get("dispensavel").getAsBoolean());
		
		prova.setNotaMinimaAprovacao(json.get("notaMinimaAprovacao").getAsInt());
		
		JsonArray jsonQuestoes = json.getAsJsonArray("pesosQuestoes");
		
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
	private void carregaRepresentacaoProjetosPesquisa(JsonObject json, Edital edital, UsuarioDAO userDAO)
	{
		JsonArray jsonProjetos = json.getAsJsonArray("projetosPesquisa");
		
		if (jsonProjetos == null)
			return;
		
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
	private ProjetoPesquisa carregaRepresentacaoProjetoPesquisa(JsonObject json, Edital edital, UsuarioDAO userDAO)
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
	private void carregaRepresentacaoProfessoresProjetoPesquisa(JsonObject json, ProjetoPesquisa projeto, UsuarioDAO userDAO)
	{
		JsonArray jsonProfessores = json.getAsJsonArray("professores");
		
		if (jsonProfessores == null)
			return;
		
		for (int i = 0; i < jsonProfessores.size(); i++)
		{
			JsonObject jsonProfessor = jsonProfessores.get(i).getAsJsonObject();
			int id = jsonProfessor.get("id").getAsInt();
			Usuario professor = userDAO.carregaUsuarioId(id);
			
			if (professor != null)
				projeto.adicionaProfessor(professor);
		}
	}

	/**
	 * Carrega a lista de provas escritas a partir da representação JSON
	 */
	private void carregaRepresentacaoProvasEscritasProjetoPesquisa(JsonObject json, ProjetoPesquisa projeto, Edital edital)
	{
		JsonArray jsonProvas = json.getAsJsonArray("provasEscritas");
		
		for (int i = 0; i < jsonProvas.size(); i++)
		{
			String codigo = jsonProvas.get(i).getAsString();
			ProvaEscrita prova = edital.pegaProvaEscritaCodigo(codigo);
			
			if (prova != null)
				projeto.adicionaProvaEscrita(prova);
		}
	}

	/**
	 * Carrega a lista de critérios de alinhamento a partir da representação JSON
	 */
	private void carregaRepresentacaoCriteriosAlinhamento(JsonObject json, Edital edital)
	{
		JsonArray jsonCriterios = json.getAsJsonArray("criteriosAlinhamento");
		
		if (jsonCriterios == null)
			return;
		
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
		criterio.setCodigo(json.get("codigo").getAsString());
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
		subcriterio.setCodigo(json.get("codigo").getAsString());
		subcriterio.setNome(json.get("nome").getAsString());
		subcriterio.setDescricao(json.get("descricao").getAsString());
		subcriterio.setPeso(json.get("peso").getAsInt());
		return subcriterio;
	}
>>>>>>> 52b3db79771b142471ce1fa8934f3ebb9506a314
}