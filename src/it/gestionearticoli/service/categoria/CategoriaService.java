package it.gestionearticoli.service.categoria;

import java.util.TreeSet;

import it.gestionearticoli.dao.categoria.CategoriaDAO;
import it.gestionearticoli.model.Categoria;

public interface CategoriaService {

	public void setCategoriaDao(CategoriaDAO categoriaDao);
	public TreeSet<Categoria> listAll() throws Exception;
	public Categoria trovaTramiteId(Long idCategoria) throws Exception;
	public int aggiorna(Categoria input) throws Exception;
	public Long inserisciNuovo(Categoria input) throws Exception;
	public int rimuovi(Categoria input) throws Exception;
	public boolean sincronizzaElencoArticoli(Categoria input) throws Exception;
	public TreeSet<Categoria> ricercaCategoria(TreeSet<String> input) throws Exception;

}
