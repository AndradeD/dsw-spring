package br.unirio.dsw.support;

import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.ProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.model.inscricao.AvaliacaoProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.service.dao.EditalDAO;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;
import br.unirio.dsw.selecaoppgi.service.json.JsonInscricaoProjetoPesquisaWriter;

/**
 * Classe responsavel por gerar as inscricoes aleatoriamente
 * 
 * @author marciobarros
 */
public class GeradorInscricoes
{
	/**
	 * Programa principal
	 */
	public static final void main(String[] args)
	{
		new GeradorInscricoes().executa();
	}

	/**
	 * Executa o gerador de inscricoes
	 */
	public void executa()
	{
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		EditalDAO editalDAO = new EditalDAO();
		
		Edital editalMestrado = editalDAO.carregaEditalNome("PPGI Mestrado 2018", usuarioDAO);
//		Edital editalDoutorado = editalDAO.carregaEditalNome("PPGI Doutorado 2018", usuarioDAO);

		System.out.println("SET SQL_SAFE_UPDATES = 0;");
		System.out.println("delete from InscricaoProvaAlinhamento;");
		System.out.println("delete from InscricaoProvaEscrita;");
		System.out.println("delete from Inscricao;");
		System.out.println();
		
		geraComandosInscricao("Fulano 0001", editalMestrado);
		geraComandosInscricao("Fulano 0002", editalMestrado);
		geraComandosInscricao("Fulano 0003", editalMestrado);
		geraComandosInscricao("Fulano 0004", editalMestrado);
	}

	/**
	 * Publica os comandos refererentes a uma inscrição
	 */
	private void geraComandosInscricao(String nome, Edital edital)
	{
		InscricaoEdital inscricao = geraInscricao(edital);

		// Cabecalho
		System.out.println("#");
		System.out.println("# Nova Inscricao");
		System.out.println("#");
		System.out.println();
		
		// Cria o usuário
		String email = nome.replace(" ", "").toLowerCase() + "@somewhere.com";
		System.out.println("CALL UsuarioInsere(\"" + nome + "\", \"" + email + "\", \"$2a$10$HflP3AJrzwM.he3.gW78a.FZvW6uVpfSiwWGmVVwTTQL78REd.6UO\", 0, @id);");
		System.out.println();
		
		// Calcula dados da inscricao
		int cotaNegros = inscricao.isCotaNegros() ? 1 : 0;
		int cotaDeficientes = inscricao.isCotaDeficientes() ? 1 : 0;
		String jsonProjetos = new JsonInscricaoProjetoPesquisaWriter().execute(inscricao).toString().replace("\"", "\\\"");

		// Publica a inscricao
		System.out.println("INSERT INTO Inscricao(dataRegistro, dataAtualizacao, idEdital, idCandidato, cotaNegros, cotaDeficientes, jsonProjetos)");
		System.out.println("SELECT NOW(), NOW(), " + edital.getId() + ", id, " + cotaNegros + ", " + cotaDeficientes + ", '" + jsonProjetos + "'");
		System.out.println("FROM Usuario WHERE nome = '" + nome + "';");
		System.out.println();

		// Pega o identificador da inscricao
		System.out.println("SET @idInscricao = LAST_INSERT_ID();");
		System.out.println();
		
		// Publica as provas
		for (AvaliacaoProvaEscrita prova : inscricao.getAvaliacoesProvasEscritas())
		{
			System.out.println("INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)");
			System.out.println("VALUES (@idInscricao, '" + prova.getProvaEscrita().getCodigo() + "', 0, 0, '', '');");
			System.out.println();
		}
		
		// Publica as avaliacoes de projeto
		for (InscricaoProjetoPesquisa projeto : inscricao.getInscricoesProjetoPesquisa())
		{
			System.out.println("INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)");
			System.out.println("VALUES (@idInscricao, '" + projeto.getProjetoPesquisa().getCodigo() + "', 0, 0, '', '');");
			System.out.println();
		}
	}

	/**
	 * Cria uma inscrição básica para um edital
	 */
	private InscricaoEdital geraInscricao(Edital edital)
	{
		// Cria a inscricao de forma aleatória
		InscricaoEdital inscricao = new InscricaoEdital(edital);
		inscricao.setCotaNegros(Math.random() > 0.5);
		inscricao.setCotaDeficientes(Math.random() > 0.5);
		
		// Seleciona os projetos de pesquisa de forma aleatória
		int numeroProjetos = 1 + (int) Math.floor(Math.random() * 4);
		
		for (int i = 0; i < numeroProjetos; i++)
		{
			int indice = (int) Math.floor(Math.random() * edital.contaProjetosPesquisa());
			ProjetoPesquisa projeto = edital.pegaProjetoPesquisaCodigo(indice);
			
			if (inscricao.pegaInscricaoProjetoPesquisa(projeto) == null)
				inscricao.adicionaInscricaoProjetoPesquisa(projeto, "minhas intencoes de pesquisa");
		}
		
		return inscricao;
	}
}