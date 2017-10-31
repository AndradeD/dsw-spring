package br.unirio.dsw.selecaoppgi;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.CriterioAlinhamento;
import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.ProjetoPesquisa;
import br.unirio.dsw.selecaoppgi.model.edital.ProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.usuario.PapelUsuario;
import br.unirio.dsw.selecaoppgi.model.usuario.Usuario;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;
import br.unirio.dsw.selecaoppgi.service.json.JsonEditalReader;
import br.unirio.dsw.selecaoppgi.service.json.JsonEditalWriter;
import br.unirio.dsw.selecaoppgi.utils.JsonUtils;

public class TestEditalJson
{
	private UsuarioDAO userDAO;
	private Usuario fulano;
	private Usuario cicrano;

	@Before
	public void setup()
	{
		fulano = new Usuario("Fulano", "fulano@somewhere.com", "fulano", PapelUsuario.ROLE_BASIC, false);
		fulano.setId(1);
		
		cicrano = new Usuario("Cicrano", "cicrano@somewhere.com", "cicrano", PapelUsuario.ROLE_BASIC, false);
		cicrano.setId(2);
		
		userDAO = mock(UsuarioDAO.class);
		when(userDAO.carregaUsuarioId(fulano.getId())).thenReturn(fulano);
		when(userDAO.carregaUsuarioId(cicrano.getId())).thenReturn(cicrano);
	}
	
	@Test
	public void testBasico()
	{
		Edital edital = new Edital();
		edital.setId(10);
		edital.setNome("Primeiro edital");
		edital.setNotaMinimaAlinhamento(70);
		
		comparaRepresentacaoJson(edital);
	}

	@Test
	public void testComissaoSelecao()
	{
		Edital edital = new Edital();
		edital.setId(10);
		edital.setNome("Primeiro edital");
		edital.setNotaMinimaAlinhamento(70);
		
		edital.adicionaComissaoSelecao(fulano);
		edital.adicionaComissaoSelecao(cicrano);
		
		comparaRepresentacaoJson(edital);
	}

	@Test
	public void testComissaoRecurso()
	{
		Edital edital = new Edital();
		edital.setId(10);
		edital.setNome("Primeiro edital");
		edital.setNotaMinimaAlinhamento(70);
		
		edital.adicionaComissaoRecurso(fulano);
		
		comparaRepresentacaoJson(edital);
	}

	@Test
	public void testProvaEscrita()
	{
		Edital edital = new Edital();
		edital.setId(10);
		edital.setNome("Primeiro edital");
		edital.setNotaMinimaAlinhamento(70);
		
		ProvaEscrita prova = edital.adicionaProvaEscrita("P1", "Prova 1", true, 70);
		prova.adicionaQuestao(30);
		prova.adicionaQuestao(20);
		prova.adicionaQuestao(50);
		
		comparaRepresentacaoJson(edital);
	}

	@Test
	public void testDuasProvasEscritas()
	{
		Edital edital = new Edital();
		edital.setId(10);
		edital.setNome("Primeiro edital");
		edital.setNotaMinimaAlinhamento(70);
		
		ProvaEscrita prova1 = edital.adicionaProvaEscrita("P1", "Prova 1", true, 70);
		prova1.adicionaQuestao(30);
		prova1.adicionaQuestao(20);
		prova1.adicionaQuestao(50);
		
		ProvaEscrita prova2 = edital.adicionaProvaEscrita("P2", "Prova 2", false, 50);
		prova2.adicionaQuestao(10);
		prova2.adicionaQuestao(90);
		
		comparaRepresentacaoJson(edital);
	}

	@Test
	public void testProjetoPesquisa()
	{
		Edital edital = new Edital();
		edital.setId(10);
		edital.setNome("Primeiro edital");
		edital.setNotaMinimaAlinhamento(70);
		
		ProvaEscrita prova1 = edital.adicionaProvaEscrita("P1", "Prova 1", true, 70);
		prova1.adicionaQuestao(30);
		prova1.adicionaQuestao(20);
		prova1.adicionaQuestao(50);
		
		ProvaEscrita prova2 = edital.adicionaProvaEscrita("P2", "Prova 2", false, 50);
		prova2.adicionaQuestao(10);
		prova2.adicionaQuestao(90);
		
		ProjetoPesquisa projeto1 = edital.adicionaProjetoPesquisa("PP1", "Projeto Pesquisa 1", true);
		projeto1.adicionaProvaEscrita(prova1);
		
		ProjetoPesquisa projeto2 = edital.adicionaProjetoPesquisa("PP2", "Projeto Pesquisa 2", false);
		projeto2.adicionaProvaEscrita(prova1);
		projeto2.adicionaProvaEscrita(prova2);
		
		comparaRepresentacaoJson(edital);
	}

	@Test
	public void testCriteriosAlinhamento()
	{
		Edital edital = new Edital();
		edital.setId(10);
		edital.setNome("Primeiro edital");
		edital.setNotaMinimaAlinhamento(70);
		
		ProvaEscrita prova1 = edital.adicionaProvaEscrita("P1", "Prova 1", true, 70);
		prova1.adicionaQuestao(30);
		prova1.adicionaQuestao(20);
		prova1.adicionaQuestao(50);
		
		ProvaEscrita prova2 = edital.adicionaProvaEscrita("P2", "Prova 2", false, 50);
		prova2.adicionaQuestao(10);
		prova2.adicionaQuestao(90);
		
		ProjetoPesquisa projeto1 = edital.adicionaProjetoPesquisa("PP1", "Projeto Pesquisa 1", true);
		projeto1.adicionaProvaEscrita(prova1);
		
		ProjetoPesquisa projeto2 = edital.adicionaProjetoPesquisa("PP2", "Projeto Pesquisa 2", false);
		projeto2.adicionaProvaEscrita(prova1);
		projeto2.adicionaProvaEscrita(prova2);
		
		CriterioAlinhamento criterio1 = edital.adicionaCriterioAlinhamento("CC1", "Criterio Alinhamento 1", 70, 70, false);
		criterio1.adicionaSubcriterio("SC11", "Subcriterio 1.1", "Descrição 1.1", 70);
		criterio1.adicionaSubcriterio("SC12", "Subcriterio 1.2", "Descrição 1.2", 30);
		edital.adicionaCriterioAlinhamento(criterio1);
		
		CriterioAlinhamento criterio2 = edital.adicionaCriterioAlinhamento("CC2", "Criterio Alinhamento 2", 70, 90, false);
		criterio2.adicionaSubcriterio("SC21", "Subcriterio 2.1", "Descrição 2.1", 70);
		criterio2.adicionaSubcriterio("SC22", "Subcriterio 2.2", "Descrição 2.2", 20);
		criterio2.adicionaSubcriterio("SC23", "Subcriterio 2.3", "Descrição 2.3", 10);
		edital.adicionaCriterioAlinhamento(criterio2);
		
		comparaRepresentacaoJson(edital);
	}

	private void comparaRepresentacaoJson(Edital edital)
	{
		JsonEditalWriter writer = new JsonEditalWriter();
		JsonObject jsonOriginal = writer.execute(edital);
		
		JsonEditalReader reader = new JsonEditalReader();
		Edital editalClone = reader.execute(jsonOriginal, userDAO);
		
		JsonObject jsonClonado = writer.execute(editalClone);
		assertTrue(JsonUtils.compara(jsonOriginal, jsonClonado));
	}
}