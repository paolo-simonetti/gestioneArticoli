package it.gestionearticoli.model;

import it.gestionearticoli.service.MyServiceFactory;

public class Articolo implements Comparable<Articolo>{

	private Long id;
	private String codice;
	private String descrizione;
	private Integer prezzo;
	private Categoria categoriaDiAfferenza;
	
	public Categoria getCategoriaDiAfferenza() {
		return categoriaDiAfferenza;
	}

	public void setCategoriaDiAfferenza(Categoria categoriaDiAfferenza) {
		this.categoriaDiAfferenza = categoriaDiAfferenza;
	}


	public Articolo() {}
	
	public Articolo(String codice, String descrizione, Integer prezzo, Categoria categoria) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		Categoria categoriaDiAppartenenza=null;
		try {
			categoriaDiAppartenenza=MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(categoria.getIdCategoria());			
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (categoriaDiAppartenenza==null) {
			System.err.println("La FK di Categoria impostata nel costruttore non ha corrispondenze tra gli id della tabella Categoria!");
			this.categoriaDiAfferenza=null;
		} else {
			this.categoriaDiAfferenza=categoriaDiAppartenenza;
			categoriaDiAppartenenza.incrementaElencoArticoli(this);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	@Override
	public int compareTo(Articolo articolo) {
		return this.codice.compareTo(articolo.getCodice());
	}

}
