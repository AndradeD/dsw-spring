package br.unirio.dsw.selecaoppgi.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.ProvaEscrita;
import br.unirio.dsw.selecaoppgi.model.usuario.Usuario;
import br.unirio.dsw.selecaoppgi.service.dao.EditalDAO;
import br.unirio.dsw.selecaoppgi.service.dao.UsuarioDAO;
import br.unirio.dsw.selecaoppgi.service.message.ExposedResourceMessageBundleSource;
import br.unirio.dsw.selecaoppgi.utils.JsonUtils;
import br.unirio.dsw.selecaoppgi.view.edital.EditalForm;
import br.unirio.dsw.selecaoppgi.view.edital.ProvaEscritaForm;

/**
 * Controller responsável pelo gerenciamento de editais
 * 
 * @author marciobarros
 */
@Controller
public class EditalController
{
    @Autowired
    private ExposedResourceMessageBundleSource messageSource;
    
	@Autowired
	private UsuarioDAO userDAO; 

	@Autowired
	private EditalDAO editalDAO; 

	/**
	 * Ação que redireciona o usuário para a lista de editais
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/list", method = RequestMethod.GET)
	public ModelAndView mostraPaginaLista()
	{
		return new ModelAndView("edital/list");
	}

	/**
	 * Ação AJAX que lista todos os editais
	 */
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital", method = RequestMethod.GET, produces = "application/json")
	public String lista(@ModelAttribute("page") int pagina, @ModelAttribute("size") int tamanho, @ModelAttribute("nome") String filtroNome)
	{
		List<Edital> editais = editalDAO.lista(pagina, tamanho, filtroNome);
		int total = editalDAO.conta(filtroNome);
		
		Gson gson = new Gson();
		JsonArray jsonEditais = new JsonArray();
		
		for (Edital edital : editais)
			jsonEditais.add(gson.toJsonTree(edital));
		
		JsonObject root = new JsonObject();
		root.addProperty("Result", "OK");
		root.addProperty("TotalRecordCount", total);
		root.add("Records", jsonEditais);
		return root.toString();
	}

	/**
	 * Ação AJAX que retorna o resumo dos editais
	 */
	@ResponseBody
	@RequestMapping(value = "/edital/summary", method = RequestMethod.GET, produces = "application/json")
	public String geraResumos()
	{
		List<Edital> editais = editalDAO.lista(0, 100000, "");
		JsonArray jsonEditais = new JsonArray();
		
		for (Edital edital : editais)
		{
			JsonObject jsonEdital = new JsonObject();
			jsonEdital.addProperty("id", edital.getId());
			jsonEdital.addProperty("nome", edital.getNome());
			jsonEditais.add(jsonEdital);
		}
		
		return JsonUtils.ajaxSuccess(jsonEditais);
	}
	
	/**
	 * Ação AJAX que troca a senha do usuário logado
	 */
	@ResponseBody
	@RequestMapping(value = "/edital/muda/{id}", method = RequestMethod.POST)
	public String mudaSelecionado(@PathVariable("id") int id, Locale locale)
	{
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (usuario == null)
			return JsonUtils.ajaxError(messageSource.getMessage("edital.muda.edital.selecionado.erro.usuario.nao.encontrado", null, locale));

		usuario.setIdEdital(id);
        userDAO.mudaEditalSelecionado(usuario.getId(), id);
		return JsonUtils.ajaxSuccess();
	}

	/**
	 * Ação que apresenta o formulário de criação de um novo edital
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/create", method = RequestMethod.GET)
	public ModelAndView mostraPaginaCriacao()
	{
		ModelAndView model = new ModelAndView("edital/formCriacao");
		model.getModel().put("form", new EditalForm());
		return model;
	}

	/**
	 * Ação que salva os dados de um novo um edital
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/create", method = RequestMethod.POST)
	public String salvaNovo(@ModelAttribute("form") EditalForm form, BindingResult result, Locale locale)
	{
		if (form.getNome().length() == 0)
	    		result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.nome.vazio", null, locale)));
		
		if (form.getNome().length() > 80)
			result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.nome.maior.80.caracteres", null, locale)));
		
		Edital editalMesmoNome = editalDAO.carregaEditalNome(form.getNome(), userDAO);
		
		if (editalMesmoNome != null)
			result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.nome.duplicado", null, locale)));
		
		if (form.getNotaMinimaAlinhamento() <= 0)
	    		result.addError(new FieldError("form", "notaMinima", messageSource.getMessage("edital.form.nota.minima.menor.igual.zero", null, locale)));
		
		if (form.getNotaMinimaAlinhamento() >= 100)
			result.addError(new FieldError("form", "notaMinima", messageSource.getMessage("edital.form.nota.minima.maior.igual.cem", null, locale)));
		
        if (result.hasErrors())
            return "edital/formCriacao";
        
		Edital edital = new Edital();
        edital.setNome(form.getNome());
        edital.setNotaMinimaAlinhamento(form.getNotaMinimaAlinhamento());
		editalDAO.atualiza(edital);
		return "redirect:/edital/list?message=edital.form.criado";
	}

	/**
	 * Ação que apresenta o formulário de edição de um edital
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/edit/{id}", method = RequestMethod.GET)
	public ModelAndView mostraPaginaEdicao(@PathVariable("id") int id, Locale locale)
	{
		ModelAndView model = new ModelAndView("edital/formEdicao");
		Edital edital = editalDAO.carregaEditalId(id, userDAO);
		
		if (edital == null)
		{
			model.setViewName("redirect:/edital/list?message=edital.form.edital.nao.encontrado");
	        return model;
		}
		
		EditalForm form = new EditalForm();
		form.setId(edital.getId());
		form.setNome(edital.getNome());
		form.setNotaMinimaAlinhamento(edital.getNotaMinimaAlinhamento());
		
		model.getModel().put("form", form);
		model.getModel().put("edital", edital);
		return model;
	}

	/**
	 * Ação que atualiza um edital sendo editado
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/edit", method = RequestMethod.POST)
	public String salvaEdicao(@ModelAttribute EditalForm form, BindingResult result, Locale locale)
	{
		if (form.getNome().length() == 0)
	    		result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.nome.vazio", null, locale)));
		
		if (form.getNome().length() > 80)
			result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.nome.maior.80.caracteres", null, locale)));
		
		Edital edital = editalDAO.carregaEditalId(form.getId(), userDAO);
		
		if (edital == null)
			result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.edital.nao.encontrado", null, locale)));
		
		Edital editalMesmoNome = editalDAO.carregaEditalNome(form.getNome(), userDAO);
		
		if (editalMesmoNome != null && editalMesmoNome.getId() != edital.getId())
			result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.nome.duplicado", null, locale)));
		
		if (form.getNotaMinimaAlinhamento() <= 0)
	    		result.addError(new FieldError("form", "notaMinima", messageSource.getMessage("edital.form.nota.minima.menor.igual.zero", null, locale)));
		
		if (form.getNotaMinimaAlinhamento() >= 100)
			result.addError(new FieldError("form", "notaMinima", messageSource.getMessage("edital.form.nota.minima.maior.igual.cem", null, locale)));
		
        if (result.hasErrors())
            return "edital/formEdicao";
        
        edital.setNome(form.getNome());
        edital.setNotaMinimaAlinhamento(form.getNotaMinimaAlinhamento());
		editalDAO.atualiza(edital);
		return "redirect:/edital/list?message=edital.form.atualizado";
	}

	/**
	 * Ação AJAX que remove um edital
	 */
	@ResponseBody
	@RequestMapping(value = "/edital/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable("id") int id, Locale locale)
	{
		Edital edital = editalDAO.carregaEditalId(id, userDAO);
		
		if (edital == null)
			return JsonUtils.ajaxError(messageSource.getMessage("edital.lista.remocao.nao.encontrado", null, locale));

		editalDAO.remove(id);
		return JsonUtils.ajaxSuccess();
	}

	/**
	 * Ação que apresenta o formulário de criação de uma nova prova escrita
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/{id}/prova/create", method = RequestMethod.GET)
	public ModelAndView novaProvaEscrita(@PathVariable int id, Locale locale)
	{		
		ModelAndView model = new ModelAndView("edital/formProva");
		ProvaEscritaForm form = new ProvaEscritaForm();
		form.setIdEdital(id);
		form.adicionaPesoQuestao(100);
		model.getModel().put("form", form);
		return model;
	}

	/**
	 * Ação que apresenta o formulário de edição de uma prova escrita
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/{id}/prova/edit/{codigo}", method = RequestMethod.GET)
	public ModelAndView editaProvaEscrita(@PathVariable int id, @PathVariable String codigo, Locale locale)
	{
		ModelAndView model = new ModelAndView("edital/formProva");
		Edital edital = editalDAO.carregaEditalId(id, userDAO);
		
		if (edital == null)
		{
			model.setViewName("redirect:/edital/list?message=edital.form.edital.nao.encontrado");
	        return model;
		}
		
		ProvaEscrita prova = edital.pegaProvaEscritaCodigo(codigo);
		
		if (prova == null)
		{
			model.setViewName("redirect:/edital/edit/" + id + "?message=edital.form.prova.nao.encontrada");
	        return model;
		}
		
		ProvaEscritaForm form = new ProvaEscritaForm();
		form.setIdEdital(id);
		form.setCodigoOriginal(codigo);
		form.setCodigo(codigo);
		form.setNome(prova.getNome());
		form.setDispensavel(prova.isDispensavel());
		form.setNotaMinimaAprovacao(prova.getNotaMinimaAprovacao());
		form.adicionaPesosQuestoes(prova.getPesosQuestoes());
		model.getModel().put("form", form);
		return model;
	}

	/**
	 * Ação AJAX que atualiza uma prova escrita em um edital
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/prova/save", method = RequestMethod.POST)
	public String atualizaProva(@ModelAttribute("form") ProvaEscritaForm form, BindingResult result, Locale locale)
	{
		Edital edital = editalDAO.carregaEditalId(form.getIdEdital(), userDAO);
		
		if (edital == null)
			result.addError(new FieldError("form", "codigo", messageSource.getMessage("edital.form.edital.nao.encontrado", null, locale)));
		
		if (form.getCodigo().length() != 3)
	    		result.addError(new FieldError("form", "codigo", messageSource.getMessage("edital.form.prova.form.erro.codigo.invalido", null, locale)));
		
		if (form.getCodigo().compareToIgnoreCase(form.getCodigoOriginal()) != 0)
		{
			ProvaEscrita prova = edital.pegaProvaEscritaCodigo(form.getCodigo());
			
			if (prova != null)
	    			result.addError(new FieldError("form", "codigo", messageSource.getMessage("edital.form.prova.form.erro.codigo.duplicado", null, locale)));
		}
		
		if (form.getNome().length() == 0)
	    		result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.prova.form.erro.nome.branco", null, locale)));
		
		if (form.getNome().length() > 80)
			result.addError(new FieldError("form", "nome", messageSource.getMessage("edital.form.prova.form.erro.nome.longo", null, locale)));
		
		if (form.getNotaMinimaAprovacao() <= 0)
	    		result.addError(new FieldError("form", "notaMinimaAprovacao", messageSource.getMessage("edital.form.prova.form.erro.nota.minima.menor.zero", null, locale)));
		
		if (form.getNotaMinimaAprovacao() >= 100)
			result.addError(new FieldError("form", "notaMinimaAprovacao", messageSource.getMessage("edital.form.prova.form.erro.nota.minima.maior.cem", null, locale)));
		
		int soma = 0;
		
		for (int i = 0; i < form.getPesosQuestoes().size(); i++)
		{
			Integer peso = form.getPesosQuestoes().get(i);
			
			if (peso == null || peso <= 0)
		    		result.addError(new FieldError("form", "pesosQuestoes", messageSource.getMessage("edital.form.prova.form.erro.peso.negativo.zero", null, locale)));
			
			else if (peso > 100)
				result.addError(new FieldError("form", "pesosQuestoes", messageSource.getMessage("edital.form.prova.form.erro.peso.maior.cem", null, locale)));
			
			else
				soma += peso;
		}
		
		if (soma != 100)
			result.addError(new FieldError("form", "pesosQuestoes", messageSource.getMessage("edital.form.prova.form.erro.peso.soma.diferente.cem", null, locale)));
	
		ProvaEscrita prova;
		
		if (form.getCodigoOriginal().length() > 0)
		{
			prova = edital.pegaProvaEscritaCodigo(form.getCodigoOriginal());
			
			if (prova == null)
				result.addError(new FieldError("form", "codigo", messageSource.getMessage("edital.form.prova.form.erro.prova.nao.encontrada", null, locale)));
		}
		else
		{
			prova = new ProvaEscrita();
			edital.adicionaProvaEscrita(prova);
		}
		
		if (result.hasErrors())
			return "edital/formProva";

		prova.setCodigo(form.getCodigo());
		prova.setNome(form.getNome());
		prova.setNotaMinimaAprovacao(form.getNotaMinimaAprovacao());
		prova.setDispensavel(form.isDispensavel());
		prova.limpaQuestoes();
		
		for (Integer peso : form.getPesosQuestoes())
			prova.adicionaQuestao(peso);
		
		editalDAO.atualiza(edital);
		return "redirect:/edital/edit/" + form.getIdEdital() + "?message=edital.form.prova.atualizada";
	}

	/**
	 * Ação que remove uma prova escrita
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/edital/{id}/prova/remove/{codigo}", method = RequestMethod.GET)
	public String removeProvaEscrita(@PathVariable int id, @PathVariable String codigo, Locale locale)
	{
		Edital edital = editalDAO.carregaEditalId(id, userDAO);
		
		if (edital == null)
	        return "redirect:/edital/list?message=edital.form.edital.nao.encontrado";
		
		ProvaEscrita prova = edital.pegaProvaEscritaCodigo(codigo);
		
		if (prova == null)
	        return "redirect:/edital/edit/" + id + "?message=edital.form.prova.nao.encontrada";
		
		edital.removeProvaEscrita(codigo);
		editalDAO.atualiza(edital);
		return "redirect:/edital/edit/" + id + "?message=edital.form.prova.removida.sucesso";
	}

	
//	/edital/abertura
//	/edital/inscricao/encerramento
}