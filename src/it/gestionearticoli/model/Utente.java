package it.gestionearticoli.model;

import java.util.TreeMap;

public class Utente implements Comparable<Utente> {
	
	private Long idUtente;
	private String nome, cognome, codiceFiscale, username, password;
	private Ruolo ruolo;
	
	public enum Ruolo {
		ADMIN ("admin"),
		OP ("op"),
		GUEST ("guest");
		
		private String stringaRuolo;
		
		Ruolo(String stringaRuolo) {
			this.stringaRuolo=stringaRuolo;
		}
		
		public String toString() {
			return stringaRuolo;
		}
		
		public static TreeMap<String,Ruolo> conversioneRuolo=new TreeMap<>(); // quando creo nuove tipologie di utenti, le metto qui 
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	
	public Utente() {}
		
	public Utente(String nome, String cognome, String codiceFiscale, String username, String password, Ruolo ruolo) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.username = username;
		this.password = password;
		this.ruolo = ruolo;
	}

	@Override
	public int compareTo(Utente utente) {
		return cognome.compareTo(utente.getCognome());
	}


}
