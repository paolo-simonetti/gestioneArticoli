package it.gestionearticoli.dao.utente;

import java.sql.Connection;
import java.util.TreeSet;

import it.gestionearticoli.dao.IBaseDAO;
import it.gestionearticoli.model.Utente;

public interface UtenteDAO extends IBaseDAO<Utente> {

	public TreeSet<Utente> list() throws Exception;
	public Utente get(Long idUtente) throws Exception;
	public int update(Utente input) throws Exception;
	public Long insert(Utente input) throws Exception;
	public int delete(Utente input) throws Exception;
	public TreeSet<Utente> findByExample(Utente input) throws Exception;
	public void setConnection(Connection connection);
	
}
