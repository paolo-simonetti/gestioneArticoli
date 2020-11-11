package it.gestionearticoli.model;

import java.util.TreeSet;

public class Categoria implements Comparable<Categoria>{
	
	private Long idCategoria;
	private String nomeCategoria;
	private TreeSet<Articolo> elencoArticoli=new TreeSet<>();
	
	public Long getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	public TreeSet<Articolo> getElencoArticoli() {
		return elencoArticoli;
	}

	public void setElencoArticoli(TreeSet<Articolo> elencoArticoli) {
		this.elencoArticoli = elencoArticoli;
	}

	public boolean incrementaElencoArticoli(Articolo articolo) {
		return this.elencoArticoli.add(articolo); // ritorna true se l'articolo non era già presente nell'elenco di questa categoria
	}

	public boolean eliminaArticoloDaElenco(Articolo articolo) {
		return this.elencoArticoli.remove(articolo); // ritorna true se l'articolo era effettivamente presente nell'elenco di questa categoria
	}
	
	public Categoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	public Categoria() {}

	@Override
	public int compareTo(Categoria categoria) {
		return nomeCategoria.compareTo(categoria.getNomeCategoria());
	}
		
}
