package br.unirio.dsw.selecaoppgi.service.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.unirio.dsw.selecaoppgi.model.edital.Edital;
import br.unirio.dsw.selecaoppgi.model.edital.StatusEdital;
import br.unirio.dsw.selecaoppgi.service.json.JsonEditalReader;
import br.unirio.dsw.selecaoppgi.service.json.JsonEditalWriter;

/**
 * Classe responsavel pela persistencia de editais
 * 
 * @author Marcio Barros
 */
@Service("editalDAO")
public class EditalDAO extends AbstractDAO
{
	/**
	 * Carrega um resumo com os dados de um edital
	 */
	private Edital carregaResumo(ResultSet rs, Edital edital) throws SQLException
	{		
		int id = rs.getInt("id");
		edital.setId(id);
		
		String nome = rs.getString("nome");
		edital.setNome(nome);
		
		StatusEdital status = StatusEdital.get(rs.getInt("status"));
		edital.setStatus(status);

		return edital;
	}

	/**
	 * Carrega os dados completos de um edital
	 */
	private Edital carrega(ResultSet rs, UsuarioDAO userDAO) throws SQLException
	{		
		String sJson = rs.getString("json");
		
		if (sJson.length() > 0)
		{
			JsonObject json = (JsonObject) new JsonParser().parse(sJson);
			JsonEditalReader reader = new JsonEditalReader();
			Edital edital = reader.execute(json, userDAO);
			return carregaResumo(rs, edital);
		}

		Edital edital = new Edital();
		return carregaResumo(rs, edital);
	}

	/**
	 * Carrega um edital, dado seu identificador
	 */
	public Edital carregaEditalId(int id, UsuarioDAO userDAO)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Edital WHERE id = ?");
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			Edital item = rs.next() ? carrega(rs, userDAO) : null;
			
			c.close();
			return item;

		} catch (SQLException e)
		{
			log("EditalDAO.getEditalId: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Carrega um edital, dado seu nome\
	 */
	public Edital carregaEditalNome(String nome, UsuarioDAO userDAO)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Edital WHERE nome = ?");
			ps.setString(1, nome);
			
			ResultSet rs = ps.executeQuery();
			Edital item = rs.next() ? carrega(rs, userDAO) : null;
			
			c.close();
			return item;

		} catch (SQLException e)
		{
			log("EditalDAO.getEditalNome: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Conta o numero de editais que atendem a um filtro
	 */
	public int conta(String filtroNome)
	{
		String SQL = "SELECT COUNT(*) " + 
					 "FROM Edital " + 
					 "WHERE nome LIKE ? ";
		
		Connection c = getConnection();
		
		if (c == null)
			return 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + filtroNome + "%");

			ResultSet rs = ps.executeQuery();
			int count = rs.next() ? rs.getInt(1) : 0;

			c.close();
			return count;

		} catch (SQLException e)
		{
			log("EditalDAO.conta: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * Retorna a lista de editais registrados no sistema que atendem a um filtro
	 */
	public List<Edital> lista(int pagina, int tamanhoPagina, String filtroNome)
	{
		String SQL = "SELECT id, nome, status " + 
					 "FROM Edital " + 
					 "WHERE nome LIKE ? " +
					 "ORDER BY dataAtualizacao DESC " +
					 "LIMIT ? OFFSET ?";

		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		List<Edital> lista = new ArrayList<Edital>();
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + filtroNome + "%");
			ps.setInt(2, tamanhoPagina);
			ps.setInt(3, pagina * tamanhoPagina);
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				Edital item = carregaResumo(rs, new Edital());
				lista.add(item);
			}

			c.close();

		} catch (SQLException e)
		{
			log("EditalDAO.lista: " + e.getMessage());
		}
		    
		return lista;
	}

	/**
	 * Atualiza um edital no sistema
	 */
	public boolean atualiza(Edital edital)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;

		try
		{
			JsonEditalWriter writer = new JsonEditalWriter();
			String jsonEdital = writer.execute(edital).toString();
			
			CallableStatement cs = c.prepareCall("{call EditalAtualiza(?, ?, ?, ?, ?)}");
			cs.setInt(1, edital.getId());
			cs.setString(2, edital.getNome());
			cs.setInt(3, edital.getStatus().getCodigo());
			cs.setString(4, jsonEdital);
			cs.registerOutParameter(5, Types.INTEGER);
			
			cs.execute();
			edital.setId(cs.getInt(5));
			
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("EditalDAO.atualiza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Remove um edital no sistema
	 */
	public boolean remove(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;

		try
		{
			CallableStatement cs = c.prepareCall("{call EditalRemove(?)}");
			cs.setInt(1, id);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("EditalDAO.remove: " + e.getMessage());
			return false;
		}
	}
}