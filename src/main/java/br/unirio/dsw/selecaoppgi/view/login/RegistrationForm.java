package br.unirio.dsw.selecaoppgi.view.login;

import lombok.Data;

/**
 * Classe do formulário de registro de um usuário
 * 
 * @author marciobarros
 */
public @Data class RegistrationForm
{
	private String name = "";
	private String email = "";
	private String password = "";
	private String repeatPassword = "";
}