package it.gestionearticoli.dao;

import java.sql.Connection;
import java.util.Set;

public interface IBaseDAO<T> {

	public Set<T> list() throws Exception;

	public T get(Long id) throws Exception;

	public int update(T input) throws Exception;

	public Long insert(T input) throws Exception;

	public int delete(T input) throws Exception;

	/*public Set<T> findByExample(T input) throws Exception; L'ho commentato perché il "findByExample" per 
	  categoria (TreeSet<Categoria> ricercaCategoria(TreeSet<String>)) e per articolo hanno firme diverse 
	
	*/
	//questo mi serve per la injection della connection
	public void setConnection(Connection connection);

}
