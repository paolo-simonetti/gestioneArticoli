package it.gestionearticoli.service;

import it.gestionearticoli.dao.articolo.ArticoloDAO;
import it.gestionearticoli.dao.articolo.ArticoloDAOImpl;
import it.gestionearticoli.dao.categoria.CategoriaDAO;
import it.gestionearticoli.dao.categoria.CategoriaDAOImpl;
import it.gestionearticoli.dao.utente.UtenteDAO;
import it.gestionearticoli.dao.utente.UtenteDAOImpl;
import it.gestionearticoli.service.articolo.ArticoloService;
import it.gestionearticoli.service.articolo.ArticoloServiceImpl;
import it.gestionearticoli.service.categoria.CategoriaService;
import it.gestionearticoli.service.categoria.CategoriaServiceImpl;
import it.gestionearticoli.service.utente.UtenteService;
import it.gestionearticoli.service.utente.UtenteServiceImpl;

public class MyServiceFactory {

	// implementiamo il singleton in modo da evitare
	// proliferazione di riferimenti
	private static ArticoloService ARTICOLO_SERVICE_INSTANCE = null;
	private static ArticoloDAO ARTICOLODAO_INSTANCE = null;
	private static CategoriaService CATEGORIA_SERVICE_INSTANCE = null;
	private static CategoriaDAO CATEGORIADAO_INSTANCE = null;
	private static UtenteService UTENTE_SERVICE_INSTANCE = null;
	private static UtenteDAO UTENTEDAO_INSTANCE = null;

	
	
	public static ArticoloService getArticoloServiceInstance() {
		if (ARTICOLO_SERVICE_INSTANCE == null)
			ARTICOLO_SERVICE_INSTANCE = new ArticoloServiceImpl();

		if (ARTICOLODAO_INSTANCE == null)
			ARTICOLODAO_INSTANCE = new ArticoloDAOImpl();

		ARTICOLO_SERVICE_INSTANCE.setArticoloDao(ARTICOLODAO_INSTANCE);

		return ARTICOLO_SERVICE_INSTANCE;
	}

	public static CategoriaService getCategoriaServiceInstance() {
		if (CATEGORIA_SERVICE_INSTANCE == null)
			CATEGORIA_SERVICE_INSTANCE = new CategoriaServiceImpl();

		if (CATEGORIADAO_INSTANCE == null)
			CATEGORIADAO_INSTANCE = new CategoriaDAOImpl();

		CATEGORIA_SERVICE_INSTANCE.setCategoriaDao(CATEGORIADAO_INSTANCE);

		return CATEGORIA_SERVICE_INSTANCE;
	}

	public static UtenteService getUtenteServiceInstance() {
		if (UTENTE_SERVICE_INSTANCE == null)
			UTENTE_SERVICE_INSTANCE = new UtenteServiceImpl();

		if (UTENTEDAO_INSTANCE == null)
			UTENTEDAO_INSTANCE = new UtenteDAOImpl();

		UTENTE_SERVICE_INSTANCE.setUtenteDao(UTENTEDAO_INSTANCE);

		return UTENTE_SERVICE_INSTANCE;
	}


}
