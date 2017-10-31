package br.unirio.dsw.selecaoppgi.model.usuario;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um usuário do sistema
 * 
 * @author marciobarros
 */
public class Usuario extends org.springframework.security.core.userdetails.User
{
	private static final long serialVersionUID = 7512107428170018274L;

	private @Setter @Getter int id;
	private @Setter @Getter String nome;
	private @Setter @Getter PapelUsuario papel;
	private @Setter @Getter DateTime dataUltimoLogin;
	private @Setter @Getter int contadorLoginFalhas;
	private @Setter @Getter String tokenLogin;
	private @Setter @Getter DateTime dataTokenLogin;
	private @Setter @Getter int idEdital;

	/**
	 * Inicializa um usuário
	 */
	public Usuario(String name, String email, String password, PapelUsuario papel, boolean locked)
	{
        super(email, password, true, true, true, !locked, createAuthoritiesFromBasicRole(papel));
		this.id = -1;
		this.nome = name;
		this.papel = papel;
		this.dataUltimoLogin = null;
		this.contadorLoginFalhas = 0;
		this.tokenLogin = "";
		this.dataTokenLogin = null;
		this.idEdital = -1;
	}

	/**
	 * Cria os direitos de acesso relacionado ao papel básico do usuário
	 */
	private static Set<GrantedAuthority> createAuthoritiesFromBasicRole(PapelUsuario papel)
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		if (papel != null && papel != PapelUsuario.ROLE_BASIC)
	        authorities.add(new SimpleGrantedAuthority(papel.toString()));
		
        authorities.add(new SimpleGrantedAuthority(PapelUsuario.ROLE_BASIC.toString()));
		return authorities;
	}
	
	/**
	 * Retorna a data do último login formatada
	 */
	public String getDataUltimoLoginFormatada()
	{
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
		return dtf.print(dataUltimoLogin);
	}
}