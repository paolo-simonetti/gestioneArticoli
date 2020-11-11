package it.gestionearticoli.service.utente;

import java.util.TreeSet;

import it.gestionearticoli.dao.utente.UtenteDAO;
import it.gestionearticoli.model.Utente;

public interface UtenteService {
	public void setUtenteDao(UtenteDAO utenteDao);
	public TreeSet<Utente> listAll() throws Exception;
	public Utente trovaTramiteId(Long idInput) throws Exception;
	public int aggiorna(Utente input) throws Exception;
	public Long inserisciNuovo(Utente input) throws Exception;
	public int rimuovi(Utente input) throws Exception;
	public TreeSet<Utente> findByExample(Utente input) throws Exception;

}
