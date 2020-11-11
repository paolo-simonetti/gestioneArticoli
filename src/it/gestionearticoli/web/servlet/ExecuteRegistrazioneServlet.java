package it.gestionearticoli.web.servlet;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticoli.model.Utente;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/ExecuteRegistrazioneServlet")
public class ExecuteRegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteRegistrazioneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeUtente=request.getParameter("nome");
		String cognomeUtente=request.getParameter("cognome");
		String codiceFiscaleUtente=request.getParameter("codiceFiscale");
		String usernameUtente=request.getParameter("username");
		String passwordUtente=request.getParameter("password");
		Utente utenteDaRegistrare=new Utente();
		utenteDaRegistrare.setNome(nomeUtente);
		utenteDaRegistrare.setCognome(cognomeUtente);
		utenteDaRegistrare.setCodiceFiscale(codiceFiscaleUtente);
		utenteDaRegistrare.setUsername(usernameUtente);
		utenteDaRegistrare.setPassword(passwordUtente);
		TreeSet<Utente> listaIscritti=null;
		try {
			listaIscritti= MyServiceFactory.getUtenteServiceInstance().listAll();			
		} catch(Exception e) {
			System.err.println("Problemi nel recupero degli utenti iscritti");
			e.printStackTrace();
			request.getRequestDispatcher("registrazione.jsp").forward(request,response);
			return;
		}
		if (listaIscritti.size()==0) { // Il primo iscritto voglio che sia qualificato come admin del sito
			utenteDaRegistrare.setRuolo(Utente.Ruolo.ADMIN);
			try {
				MyServiceFactory.getUtenteServiceInstance().inserisciNuovo(utenteDaRegistrare);
				request.setAttribute("registrazioneRiuscitaMessage","Registrazione effettuata con successo!");
			} catch (Exception e) {
				System.err.println("Non sono riuscito ad aggiungere l'utente "+ nomeUtente+ " " + cognomeUtente+ " alla lista degli iscritti");		
				e.printStackTrace();
			} finally {
				request.getRequestDispatcher("welcome.jsp").forward(request, response);				
			}
		} else { // se ci sono già utenti iscritti, verifico che utenteDaRegistrare non sia già tra loro
			boolean utenteGiaRegistrato=false;
			for (Utente u:listaIscritti) {
				if (codiceFiscaleUtente.equals(u.getCodiceFiscale())) {
					utenteGiaRegistrato=true;
					break;
				}
			}
			if (utenteGiaRegistrato) {
				request.setAttribute("registrazioneGiaEffettuataMessage", "Sei già registrato! Non ricordi le tue credenziali?");
				request.getRequestDispatcher("welcome.jsp").forward(request,response);
				return;
			} else {
				utenteDaRegistrare.setRuolo(Utente.Ruolo.GUEST);
				try {
					MyServiceFactory.getUtenteServiceInstance().inserisciNuovo(utenteDaRegistrare);
				} catch (Exception e) {
					System.err.println("Non sono riuscito ad aggiungere l'utente "+ nomeUtente+ " " + cognomeUtente+ " alla lista degli iscritti");
					e.printStackTrace();
				}
				request.setAttribute("registrazioneRiuscitaMessage","Registrazione effettuata con successo!");
				request.getRequestDispatcher("welcome.jsp").forward(request, response);
			}
			
		}
	}

}
