package it.gestionearticoli.web.servlet.utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.model.Utente;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/PrepareCambiaRuoloServlet")
public class PrepareCambiaRuoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PrepareCambiaRuoloServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		if (((String) session.getAttribute("nomeUtente"))==null||((String) session.getAttribute("cognomeUtente"))==null||
				((String) session.getAttribute("codiceFiscaleUtente"))==null||!session.getAttribute("ruoloUtente").equals("admin")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		Long idUtente=Long.parseLong(request.getParameter("idUtente"));
		try {
			Utente utente=MyServiceFactory.getUtenteServiceInstance().trovaTramiteId(idUtente);
			if (utente==null) {
				request.setAttribute("dangerMessage","Utente non trovato. Non avrai cercato di inserirlo a mano, vero???");
				request.getRequestDispatcher("utente/elencoUtenti.jsp").forward(request,response);
				return;
			}
			request.setAttribute("utenteDaRiqualificare",utente);
			request.getRequestDispatcher("utente/cambiaPermessiUtente.jsp").forward(request,response);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("dangerMessage","Errore nel reperimento dell'utente richiesto");
			request.getRequestDispatcher("menu.jsp").forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("permessiMancantiMessage","E' stato rilevato un tentativo di cambiare la tipologia di richiesta al server.");
		request.getRequestDispatcher("welcome.jsp").forward(request,response);
		HttpSession session=request.getSession();
		session.invalidate();

	}

}
