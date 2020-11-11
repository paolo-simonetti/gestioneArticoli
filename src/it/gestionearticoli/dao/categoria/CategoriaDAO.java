package it.gestionearticoli.dao.categoria;

import java.sql.Connection;
import java.util.TreeSet;

import it.gestionearticoli.dao.IBaseDAO;
import it.gestionearticoli.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria> {

	public TreeSet<Categoria> list() throws Exception;
	public Categoria get(Long idCategoria) throws Exception;
	public int update(Categoria input) throws Exception;
	public Long insert(Categoria input) throws Exception;
	public int delete(Categoria input) throws Exception;
	public boolean synchroElencoArticoli(Categoria input) throws Exception;
	public TreeSet<Categoria> searchCategoria(TreeSet<String> input) throws Exception;
	public void setConnection(Connection connection);
	public Connection getConnection();
	
}
