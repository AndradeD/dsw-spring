package br.unirio.dsw.selecaoppgi.model.usuario;

import lombok.Getter;

/**
 * Enumeração dos papéis que podem ser assumidos pelos usuários
 * 
 * @author marciobarros
 */
public enum PapelUsuario 
{
    ROLE_BASIC(0),
    ROLE_OFFICE(1),
    ROLE_PROFESSOR(2),
    ROLE_ADMIN(3);
    
    private @Getter int codigo;
    
    /**
     * Inicializa um papel de usuário
     */
    private PapelUsuario(int codigo)
    {
    		this.codigo = codigo;
    }
    
    /**
     * Retorna um papel de usuário, dado seu código
     */
    public static PapelUsuario get(int codigo)
    {
		for (PapelUsuario role : values())
			if (role.getCodigo() == codigo)
				return role;

		return null;
    }
    
    /**
     * Retorna o nome do papel
     */
    public String toString()
    {
    		return name();
    }
}