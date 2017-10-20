package br.unirio.dsw.selecaoppgi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.inscricao.InscricaoEdital;
import br.unirio.dsw.selecaoppgi.service.dao.EditalDAO;
import br.unirio.dsw.selecaoppgi.service.dao.InscricaoDAO;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;

@Controller
public class HomologacaoController
{
	@Autowired
	private UsuarioDAO userDAO;

	@Autowired
	private EditalDAO editalDAO;
	
	@Autowired
	private InscricaoDAO inscricaoDAO;
	
//	/edital/homologacao/inscricao

	/**
	 * Ação que redireciona o usuário para a tela de homologação de inscrições de um edital
	 */
    @RequestMapping(value = "/edital/homologacao/inscricao/{id}", method = RequestMethod.GET)
    public ModelAndView mostraPaginaHomologacaoInscricao(@PathVariable("id") int idEdital)
    {
    	ModelAndView model = new ModelAndView("/edital/homologacao/inscricao");
    	
    	Edital edital = editalDAO.carregaEditalId(idEdital, userDAO);
    	
    	model.getModel().put("edital", edital);
    	return model;
    }
    
    /**
	 * Ação AJAX que lista todos os candidatos de um edital esperando homologacao da inscrição
	 */
	@ResponseBody
	@RequestMapping(value = "/edital/homologacao/inscricao", method = RequestMethod.GET, produces = "application/json")
	public String lista(@ModelAttribute("idEdital") int idEdital, @ModelAttribute("page") int pagina, @ModelAttribute("size") int tamanho, @ModelAttribute("nome") String filtroNome)
	{
		List<InscricaoEdital> inscricoes = inscricaoDAO.carregaAvaliacaoHomologacao(idEdital);
		int total = inscricoes.size();
		
		Gson gson = new Gson();
		JsonArray jsonInscricoes = new JsonArray();
		
		for (InscricaoEdital inscricao : inscricoes) {
			JsonElement jsonTree = gson.toJsonTree(inscricao);
			jsonInscricoes.add(jsonTree);
			System.out.println(jsonTree.toString());
		}
		
		JsonObject root = new JsonObject();
		root.addProperty("Result", "OK");
		root.addProperty("TotalRecordCount", total);
		root.add("Records", jsonInscricoes);
		return root.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/edital/homologacao/inscricao/original", method = RequestMethod.POST)
	public boolean homologaOriginal(BindingResult binding) {
		return false;
//		if (homologado) {
//			return inscricaoDAO.homologacaoInicial(id);
//		}else {
//			return inscricaoDAO.recusaHomologacaoInicial(id,justificativa);
//		}		
	}
	
	@ResponseBody
	@RequestMapping(value = "/edital/homologacao/inscricao/recurso", method = RequestMethod.POST)
	public boolean homologaRecurso(Integer id, Boolean homologado, String justificativa) {
		if (homologado) {
			return inscricaoDAO.homologacaoRecurso(id);
		}else {
			return inscricaoDAO.recusaHomologacaoRecurso(id,justificativa);
		}				
	}
	
	
//	/edital/homologacao/dispensa
	/**
	 * Ação que redireciona o usuário para a tela de homologação de dispensa de um edital
	 */
    @RequestMapping(value = "/edital/homologacao/dispensa/{id}", method = RequestMethod.GET)
    public ModelAndView mostraPaginaHomologacaoDispensa(@PathVariable("id") int idEdital)
    {
    	ModelAndView model = new ModelAndView("/edital/homologacao/dispensa");
    	
    	Edital edital = editalDAO.carregaEditalId(idEdital, userDAO);
    	
    	model.getModel().put("edital", edital);
    	return model;
    }
	
    /**
	 * Ação AJAX que lista todos os candidatos de um edital esperando homologacao da dispensa
	 */
	@ResponseBody
	@RequestMapping(value = "/edital/homologacao/dispensa", method = RequestMethod.GET, produces = "application/json")
	public String listaDispensa(@ModelAttribute("idEdital") int idEdital, @ModelAttribute("page") int pagina, @ModelAttribute("size") int tamanho, @ModelAttribute("nome") String filtroNome)
	{
		List<InscricaoEdital> dispensas = inscricaoDAO.carregaAvaliacaoDispensaProva(idEdital);
		int total = dispensas.size();
		
		Gson gson = new Gson();
		JsonArray jsonDispensas = new JsonArray();
		
		for (InscricaoEdital dispensa : dispensas)
			jsonDispensas.add(gson.toJsonTree(dispensa));
		
		JsonObject root = new JsonObject();
		root.addProperty("Result", "OK");
		root.addProperty("TotalRecordCount", total);
		root.add("Records", jsonDispensas);
		return root.toString();
	}
	
	
//	/edital/homologacao/encerramento
	
}